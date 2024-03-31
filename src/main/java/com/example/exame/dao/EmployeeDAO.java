package com.example.exame.dao;

import com.example.exame.model.Employee;

import com.example.exame.model.EmployeeProjectAssignment;
import com.example.exame.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public boolean isEmployeeAssignedToProject(Employee employee, Project project) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(ep) FROM EmployeeProjectAssignment ep " +
                        "WHERE ep.employee = :employee AND ep.project = :project", Long.class);
        query.setParameter("employee", employee);
        query.setParameter("project", project);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public Project getProjectByName(String name) {
        try {
            TypedQuery<Project> query = entityManager.createQuery(
                    "SELECT p FROM Project p WHERE p.name = :name", Project.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            // Handle exception (e.g., NoResultException if no project found)
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployeeByName(String name) {
        try {
            TypedQuery<Employee> query = entityManager.createQuery(
                    "SELECT e FROM Employee e WHERE e.name = :name", Employee.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            // Handle exception (e.g., NoResultException if no employee found)
            e.printStackTrace();
            return null;
        }
    }

    public EmployeeDAO() {
        // Initialize EntityManagerFactory
        entityManagerFactory = Persistence.createEntityManagerFactory("student_pu");
        // Create EntityManager
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<String> getEmployeeNames() {
        // Use JPQL to fetch employee names
        TypedQuery<String> query = entityManager.createQuery("SELECT e.name FROM Employee e", String.class);
        return query.getResultList();
    }

    public List<Project> getAllProjects() {
        // Use JPQL (Java Persistence Query Language) to fetch all projects
        TypedQuery<Project> query = entityManager.createQuery("SELECT p FROM Project p", Project.class);
        return query.getResultList();
    }
    public List<Employee> getAllEmployees() {
        // Use JPQL (Java Persistence Query Language) to fetch all employees
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    public void assignProject(EmployeeProjectAssignment assignment) {
        entityManager.getTransaction().begin();
        entityManager.persist(assignment);
        entityManager.getTransaction().commit();
    }


    public void deleteEmployee(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.getTransaction().begin();

            // Manually delete related records from employees_projects table
            entityManager.createQuery("DELETE FROM EmployeeProjectAssignment ep WHERE ep.employee = :employee")
                    .setParameter("employee", employee)
                    .executeUpdate();

            // Delete the employee from the database
            entityManager.remove(employee);

            entityManager.getTransaction().commit();
        }
    }

    // Ensure to close EntityManager and EntityManagerFactory when done
    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
