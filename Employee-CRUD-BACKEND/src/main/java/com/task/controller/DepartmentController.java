package com.task.controller;

import com.task.entity.Department;
import com.task.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{deptId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer deptId) {
        Optional<Department> department = departmentService.getDepartmentById(deptId);
        return department.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{deptId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer deptId, @RequestBody Department updatedDepartment) {
        Optional<Department> department = departmentService.getDepartmentById(deptId);
        if (department.isPresent()) {
            Department result = departmentService.updateDepartment(deptId, updatedDepartment);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{deptId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer deptId) {
        Optional<Department> department = departmentService.getDepartmentById(deptId);
        if (department.isPresent()) {
            departmentService.deleteDepartment(deptId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
