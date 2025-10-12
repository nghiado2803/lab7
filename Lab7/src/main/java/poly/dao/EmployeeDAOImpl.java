package poly.dao;

import java.sql.*;
import java.util.*;
import poly.entity.*;
import poly.utils.Jdbc;

public class EmployeeDAOImpl implements EmployeeDAO {

    DepartmentDAO deptDAO = new DepartmentDAOImpl();

    @Override
    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employees";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getString("Id"));
                e.setPassword(rs.getString("Password"));
                e.setFullname(rs.getString("Fullname"));
                e.setPhoto(rs.getString("Photo"));
                e.setGender(rs.getBoolean("Gender"));
                e.setBirthday(rs.getDate("Birthday"));
                e.setSalary(rs.getDouble("Salary"));
                Department d = deptDAO.findById(rs.getString("DepartmentId"));
                e.setDepartment(d);
                list.add(e);
            }

        } catch (Exception ex) {
            throw new RuntimeException("❌ Lỗi truy vấn danh sách nhân viên", ex);
        }
        return list;
    }

    @Override
    public Employee findById(String id) {
        String sql = "SELECT * FROM Employees WHERE Id=?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getString("Id"));
                e.setPassword(rs.getString("Password"));
                e.setFullname(rs.getString("Fullname"));
                e.setPhoto(rs.getString("Photo"));
                e.setGender(rs.getBoolean("Gender"));
                e.setBirthday(rs.getDate("Birthday"));
                e.setSalary(rs.getDouble("Salary"));
                Department d = deptDAO.findById(rs.getString("DepartmentId"));
                e.setDepartment(d);
                return e;
            }
        } catch (Exception ex) {
            throw new RuntimeException("❌ Lỗi tìm nhân viên theo mã", ex);
        }
        return null;
    }

    @Override
    public void create(Employee e) {
        String sql = """
            INSERT INTO Employees 
            (Id, Password, Fullname, Photo, Gender, Birthday, Salary, DepartmentId)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getId());
            ps.setString(2, e.getPassword());
            ps.setString(3, e.getFullname());
            ps.setString(4, e.getPhoto());
            ps.setBoolean(5, e.isGender());
            ps.setDate(6, new java.sql.Date(e.getBirthday().getTime()));
            ps.setDouble(7, e.getSalary());
            ps.setString(8, e.getDepartment().getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException("❌ Lỗi thêm nhân viên", ex);
        }
    }

    @Override
    public void update(Employee e) {
        String sql = """
            UPDATE Employees
            SET Password=?, Fullname=?, Photo=?, Gender=?, Birthday=?, Salary=?, DepartmentId=?
            WHERE Id=?
        """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getPassword());
            ps.setString(2, e.getFullname());
            ps.setString(3, e.getPhoto());
            ps.setBoolean(4, e.isGender());
            ps.setDate(5, new java.sql.Date(e.getBirthday().getTime()));
            ps.setDouble(6, e.getSalary());
            ps.setString(7, e.getDepartment().getId());
            ps.setString(8, e.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException("❌ Lỗi cập nhật nhân viên", ex);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Employees WHERE Id=?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException("❌ Lỗi xóa nhân viên", ex);
        }
    }
}
