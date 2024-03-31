package com.example.exame.bean;


import com.example.exame.dao.EmployeeDAO;
import com.example.exame.model.Employee;
import com.example.exame.model.EmployeeProjectAssignment;
import com.example.exame.model.Project;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.validation.constraints.Null;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped

public class EmployeeBean implements Serializable {
    private Integer id;
    private String name;
    private String email;

    private Employee employee;

    private List<Employee> employees;

    private List<String> employeeNames;
    private String selectedEmployee;
    private List<Project> projects;
    private List<String> projectNames;
    private List<String> selectedProjects;
    private int percentageImplication;
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public void affectProject() {
        Employee employee = employeeDAO.getEmployeeByName(selectedEmployee);
        List<Project> selectedProject = getProjectsByName(selectedProjects);

        if (employee != null) {
            // Assign projects to the employee with the specified percentage implication
            for (Project project : selectedProject) {
                if (!employeeDAO.isEmployeeAssignedToProject(employee, project)) {
                    EmployeeProjectAssignment assignment = new EmployeeProjectAssignment();
                    assignment.setEmployee(employee);
                    assignment.setProject(project);
                    assignment.setAffectationPercentage(percentageImplication);
                    // Save the assignment
                    employeeDAO.assignProject(assignment);
                } else {
                    // Handle duplicate project assignment (e.g., display a warning message)
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Project '" + project.getName() + "' is already assigned to the employee.", "Project '" + project.getName() + "' is already assigned to the employee."));
                    return;
                }
            }
            // Display success message if there are no errors
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projects assigned successfully!", "Projects assigned successfully!"));
        } else {
            // Display error message if the selected employee is not found
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selected employee not found!", "Selected employee not found!"));
        }
    }


    private List<Project> getProjectsByName(List<String> projectNames) {
        List<Project> projects = new ArrayList<>();
        for (String projectName : projectNames) {
            Project project = employeeDAO.getProjectByName(projectName);
            projects.add(project);
        }
        return projects;
    }


    public EmployeeBean() throws SQLException {

    }


    @PostConstruct
    public void init(){
        employeeNames = employeeDAO.getEmployeeNames(); // Assuming you have a service class for fetching employee data
        System.out.println(selectedEmployee);

        employees = employeeDAO.getAllEmployees();

        projects = employeeDAO.getAllProjects();

        // Populate projectNames list with project names
        projectNames = new ArrayList<>();
        for (Project project : projects) {
            if (project.getId() > -1) {
                projectNames.add(project.getName());
            }
        }
    }



    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public int getPercentageImplication() {
        return percentageImplication;
    }

    public void setPercentageImplication(int percentageImplication) {
        this.percentageImplication = percentageImplication;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    public List<String> getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(List<String> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public List<String> getEmployeeNames() {
        return employeeNames;
    }

    public void setEmployeeNames(List<String> employeeNames) {
        this.employeeNames = employeeNames;
    }

    public String getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(String selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
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

    public List<Employee> getEmployees() {
        return employees;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public void deleteEmployee(Employee employee) throws SQLException {
        if (employee != null) {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.deleteEmployee(employee.getId());

            employees = employeeDAO.getAllEmployees();
            employeeNames = employeeDAO.getEmployeeNames();
        } else {
            // Handle the case where employee is null
            System.out.println("Error: Employee is null");
        }
    }



}
