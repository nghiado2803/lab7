package poly.dao;

import java.util.List;
import poly.entity.Employee;

public interface EmployeeDAO {
    void create(Employee emp);
    void update(Employee emp);
    void delete(String id);
    Employee findById(String id);
    List<Employee> findAll();
}
