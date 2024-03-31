package com.example.exame.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees_projects")
public class EmployeeProjectAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "emloyee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "affectation_percentage")
    private int affectationPercentage;

    public EmployeeProjectAssignment() {
    }

    public EmployeeProjectAssignment(int id, Employee employee, Project project, int affectationPercentage) {
        this.id = id;
        this.employee = employee;
        this.project = project;
        this.affectationPercentage = affectationPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getAffectationPercentage() {
        return affectationPercentage;
    }

    public void setAffectationPercentage(int affectationPercentage) {
        this.affectationPercentage = affectationPercentage;
    }


}
