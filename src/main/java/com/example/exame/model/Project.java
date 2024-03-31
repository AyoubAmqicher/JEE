package com.example.exame.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double budget;
    @OneToMany(mappedBy = "project")
    private Set<EmployeeProjectAssignment> emloyeeAssignment = new HashSet<>();

    public Project() {
    }

    public Project(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Set<EmployeeProjectAssignment> getEmloyeeAssignment() {
        return emloyeeAssignment;
    }

    public void setEmloyeeAssignment(Set<EmployeeProjectAssignment> emloyeeAssignment) {
        this.emloyeeAssignment = emloyeeAssignment;
    }
}
