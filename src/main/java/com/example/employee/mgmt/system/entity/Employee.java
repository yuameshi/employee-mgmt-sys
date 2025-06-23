package com.example.employee.mgmt.system.entity;

import java.util.Date;

public class Employee {

    private Long id;
    private String name;
    private EmployeeSex gender;
    private String phone;
    private String email;
    private Long dept;
    private Date hireDate;
    private Date createTime;

    public enum EmployeeSex {
        MALE,
        FEMALE,
    };

    public Employee() {
    }

    public Employee(Long id, String name, EmployeeSex gender, String phone, String email, Long dept,
            Date hireDate, Date createTime) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dept = dept;
        this.hireDate = hireDate;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public EmployeeSex getGender() {
        return gender;
    }

    public void setGender(EmployeeSex sex) {
        this.gender = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDept() {
        return dept;
    }

    public void setDept(Long dept) {
        this.dept = dept;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
