package com.task.service;

import com.task.dto.EmployeeDto;
import com.task.entity.Department;
import com.task.entity.Employee;
import com.task.entity.Role;
import com.task.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import the Transactional annotation
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Inject EntityManager
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee login(String name,String pass) {
        Employee empl= employeeRepository.findByEmpNameAndPass(name,pass);
        if(empl == null) {
            throw new RuntimeException("employee is null");
        } return empl;
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Integer empId) {
        return employeeRepository.findById(empId);
    }

    @Transactional
    public Employee createEmployee(EmployeeDto employeedto) throws IOException {
        String image = employeedto.getImage();
        Employee employee = new Employee();
        Role role = new Role();
        role.setRoleId(employeedto.getRoleId());
        Department department = new Department();
        department.setDeptId(employeedto.getDeptId());
        employee.setEmpName(employeedto.getEmpName());
        byte[] passpic = null;

        if (image != null && !image.isEmpty()) {
            // Extract the Base64 data from the data URI
            String base64Data = extractBase64Data(image);

            // Decode the Base64 data
            passpic = Base64.getDecoder().decode(base64Data);
        }

        employee.setPassportPic(passpic);
        employee.setPass(employeedto.getPass());
        employee.setRole(role);
        employee.setDepartment(department);

        employeeRepository.save(employee);
        return employee;
//        // Add any additional validation or business logic before saving
//        Department department = employee.getDepartment();
//
//        // Save the Department if it's not already persisted
//        if (department.getDeptId() == null) {
//            entityManager.persist(department);
//        }
//
//        entityManager.persist(employee);
//        return employeeRepository.save(employee);
    }
    private String extractBase64Data(String dataUri) {
        // Split the data URI to get the part after the comma (which contains the Base64 data)
        String[] parts = dataUri.split(",");
        if (parts.length == 2) {
            return parts[1];
        } else {
            throw new IllegalArgumentException("Invalid data URI format");
        }
    }
    @Transactional // Add the Transactional annotation
    public Employee updateEmployee(Integer empId, Employee updatedEmployee) {
        // Add logic to check if the employee with empId exists before updating
        // Add any additional validation or business logic before updating
        updatedEmployee.setEmpid(empId);
        return employeeRepository.save(updatedEmployee);
    }

    @Transactional // Add the Transactional annotation
    public void deleteEmployee(Integer empId) {
        // Add logic to check if the employee with empId exists before deleting
        employeeRepository.deleteById(empId);
    }

    @Transactional
    public Employee uploadPassportPic(Integer empId, MultipartFile passportPicFile) {
            Optional<Employee> employeeOptional = employeeRepository.findById(empId);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                try {
                  byte[] passportPicBytes = passportPicFile.getBytes();
                  //String passportPicBytes = StringUtils.cleanPath(passportPicFile.getOriginalFilename());
                    employee.setPassportPic(passportPicBytes);
                    // Save the updated employee with the new passport picture
                    return employeeRepository.save(employee);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to upload passport picture", e);
                }
            } else {
                throw new RuntimeException("Employee not found with ID: " + empId);
            }
        }
}
