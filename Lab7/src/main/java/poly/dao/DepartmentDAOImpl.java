package poly.dao;

import java.sql.*;
import java.util.*;
import poly.entity.Department;
import poly.utils.Jdbc;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM Departments";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Department(
                    rs.getString("Id"),
                    rs.getString("Name"),
                    rs.getString("Description")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(" Lỗi truy vấn phòng ban", e);
        }
        return list;
    }

    @Override
    public Department findById(String id) {
        String sql = "SELECT * FROM Departments WHERE Id = ?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Department(
                    rs.getString("Id"),
                    rs.getString("Name"),
                    rs.getString("Description")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(" Lỗi tìm phòng ban theo mã", e);
        }
        return null;
    }

    @Override
    public void create(Department d) {
        String sql = "INSERT INTO Departments VALUES (?, ?, ?)";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getDescription());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(" Lỗi thêm phòng ban", e);
        }
    }

    @Override
    public void update(Department d) {
        String sql = "UPDATE Departments SET Name=?, Description=? WHERE Id=?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getDescription());
            ps.setString(3, d.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(" Lỗi cập nhật phòng ban", e);
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM Departments WHERE Id=?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(" Lỗi xóa phòng ban", e);
        }
    }
}
