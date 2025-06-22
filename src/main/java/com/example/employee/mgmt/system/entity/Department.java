package com.example.employee.mgmt.system.entity;

public class Department {

    private Long deptId;
    private String name;

    public Department() {
    }

    public Department(Long id, String name) {
        this.deptId = id;
        this.name = name;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long id) {
        this.deptId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

}
