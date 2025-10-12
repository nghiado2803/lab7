package poly.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import poly.dao.DepartmentDAO;
import poly.dao.DepartmentDAOImpl;
import poly.entity.Department;

@WebServlet({
    "/department/index",
    "/department/edit/*",
    "/department/create",
    "/department/update",
    "/department/delete",
    "/department/reset"
})
public class DepartmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DepartmentDAO dao = new DepartmentDAOImpl();
        Department form = new Department();

        try {
            BeanUtils.populate(form, req.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        String path = req.getServletPath();
        String message = "";

        try {
            if (path.contains("edit")) {
                // Lấy id từ URL: /department/edit/{id}
                String id = req.getPathInfo();
                if (id != null && id.length() > 1) {
                    id = id.substring(1);
                    form = dao.findById(id);
                }
            } else if (path.contains("create")) {
                dao.create(form);
                message = "Thêm mới thành công!";
                form = new Department();
            } else if (path.contains("update")) {
                dao.update(form);
                message = "Cập nhật thành công!";
            } else if (path.contains("delete")) {
                dao.deleteById(form.getId());
                message = "Xóa thành công!";
                form = new Department();
            } else if (path.contains("reset")) {
                form = new Department();
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Lỗi: " + e.getMessage();
        }

        // Truy vấn tất cả
        List<Department> list = dao.findAll();
        req.setAttribute("list", list);
        req.setAttribute("item", form);
        req.setAttribute("message", message);

        // Forward tới JSP
        req.getRequestDispatcher("/views/department.jsp").forward(req, resp);
    }
}
