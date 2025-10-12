package poly.entity;

import java.util.Date;

public class Employee {
    private String id;
    private String password;
    private String fullname;
    private String photo;
    private boolean gender;
    private Date birthday;
    private double salary;
    private Department department;

    public Employee() {}

    public Employee(String id, String password, String fullname, String photo, boolean gender,
                    Date birthday, double salary, Department department) {
        this.id = id;
        this.password = password;
        this.fullname = fullname;
        this.photo = photo;
        this.gender = gender;
        this.birthday = birthday;
        this.salary = salary;
        this.department = department;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
