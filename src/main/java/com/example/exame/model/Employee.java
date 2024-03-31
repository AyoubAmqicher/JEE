package com.example.exame.model;

import jakarta.persistence.*;

import java.util.*;

@Entity


public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name",nullable = false,length = 150)
    private String name;

    @Column(name = "email",nullable = false,length = 250)

    private String email;

    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    public enum Post {
        MANAGER,
        DEV,
        TEST,
        DEVOPS,
        TECH_LEAD
    }

    @Enumerated(EnumType.STRING)
    private Post post;
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeProjectAssignment> projectAssignments = new HashSet<>();

    public String projectsAsString() {
        StringBuilder projectsString = new StringBuilder();
        for (EmployeeProjectAssignment assignment : projectAssignments) {
            if (projectsString.length() > 0) {
                projectsString.append(", ");
            }
            projectsString.append(assignment.getProject().getName());
        }
        return projectsString.toString();
    }

    public Employee() {
    }

    public Employee(String name, String email, Post post) {
        this.name = name;
        this.email = email;
        this.post = post;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<EmployeeProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public void setProjectAssignments(Set<EmployeeProjectAssignment> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }
}
