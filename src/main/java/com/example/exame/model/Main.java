package com.example.exame.model;

import com.example.exame.dao.EmployeeDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        dao.deleteEmployee(1);
        String names = dao.getEmployeeNames().get(1);
        System.out.println(names);

        String name = dao.getAllProjects().get(1).getName();
        System.out.println(name);

    }
}
