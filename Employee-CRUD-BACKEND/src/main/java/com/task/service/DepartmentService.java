package com.task.service;

import com.task.entity.Department;
import com.task.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Integer deptId) {
        return departmentRepository.findById(deptId);
    }

    public Department createDepartment(Department department) {
        // Add any additional validation or business logic before saving
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Integer deptId, Department updatedDepartment) {
        // Add logic to check if the department with deptId exists before updating
        // Add any additional validation or business logic before updating
        updatedDepartment.setDeptId(deptId);
        return departmentRepository.save(updatedDepartment);
    }

    public void deleteDepartment(Integer deptId) {
        // Add logic to check if the department with deptId exists before deleting
        departmentRepository.deleteById(deptId);
    }
}
