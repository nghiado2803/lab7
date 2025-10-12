package poly.servlet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poly.dao.*;
import poly.entity.*;

@WebServlet({"/employee", "/employee/create", "/employee/update", "/employee/delete", "/employee/edit/*", "/employee/reset"})
@MultipartConfig
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private DepartmentDAO departmentDAO = new DepartmentDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (uri.contains("edit")) {
            edit(req, resp);
        } else {
            list(req, resp);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", employeeDAO.findAll());
        req.setAttribute("departments", departmentDAO.findAll());
        req.getRequestDispatcher("/views/employee.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getPathInfo();
        if (id != null && id.length() > 1) {
            id = id.substring(1);
            Employee e = employeeDAO.findById(id);
            req.setAttribute("item", e);
        }
        list(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();

        if (uri.contains("create")) {
            create(req, resp);
        } else if (uri.contains("update")) {
            update(req, resp);
        } else if (uri.contains("delete")) {
            delete(req, resp);
        } else if (uri.contains("reset")) {
            reset(req, resp);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Employee e = getFormData(req);
            employeeDAO.create(e);
            req.setAttribute("message", "✅ Thêm mới thành công!");
        } catch (Exception ex) {
            req.setAttribute("message", "❌ Lỗi thêm mới: " + ex.getMessage());
        }
        list(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Employee e = getFormData(req);
            employeeDAO.update(e);
            req.setAttribute("message", "✅ Cập nhật thành công!");
        } catch (Exception ex) {
            req.setAttribute("message", "❌ Lỗi cập nhật: " + ex.getMessage());
        }
        list(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            employeeDAO.delete(id);
            req.setAttribute("message", "✅ Xóa thành công!");
        } catch (Exception ex) {
            req.setAttribute("message", "❌ Lỗi xóa: " + ex.getMessage());
        }
        list(req, resp);
    }

    private void reset(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("item", new Employee());
        list(req, resp);
    }

    // Lấy dữ liệu từ form
    private Employee getFormData(HttpServletRequest req) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Employee e = new Employee();
        e.setId(req.getParameter("id"));
        e.setPassword(req.getParameter("password"));
        e.setFullname(req.getParameter("fullname"));
        e.setGender(Boolean.parseBoolean(req.getParameter("gender")));
        e.setBirthday(sdf.parse(req.getParameter("birthday")));
        e.setSalary(Double.parseDouble(req.getParameter("salary")));

        // xử lý ảnh
        Part part = req.getPart("photoFile");
        String filename = part != null ? part.getSubmittedFileName() : null;
        if (filename != null && !filename.isEmpty()) {
            String uploadPath = req.getServletContext().getRealPath("/uploads");
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();
            part.write(uploadPath + "/" + filename);
            e.setPhoto(filename);
        } else {
            e.setPhoto(req.getParameter("photo"));
        }

        // lấy phòng ban
        String deptId = req.getParameter("departmentId");
        Department dept = departmentDAO.findById(deptId);
        e.setDepartment(dept);

        return e;
    }
}
