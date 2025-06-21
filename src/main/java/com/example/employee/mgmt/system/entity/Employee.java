package com.example.employee.mgmt.system.entity;

import java.util.Date;

public class Employee {

    private Long id;
    private String name;
    private String password;
    private EmployeeSex gender;
    private String phone;
    private String email;
    private Integer dept;
    private Date hireDate;
    private Date createTime;

    public enum EmployeeSex {
        MALE,
        FEMALE,
    };

    public Employee() {
    }

    public Employee(Long id, String name, String password, EmployeeSex gender, String phone, String email, Integer dept,
            Date hireDate, Date createTime) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
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
