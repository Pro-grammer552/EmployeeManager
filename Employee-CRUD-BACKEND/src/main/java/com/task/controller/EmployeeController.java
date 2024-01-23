package com.task.controller;

import com.task.dto.EmployeeDto;
import com.task.entity.Employee;
import com.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer empId) {
        Optional<Employee> employee = employeeService.getEmployeeById(empId);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//  @PostMapping("/imagesave")
//  public ResponseEntity<Employee> createEmployee(@RequestParam("id") Integer id, @RequestParam(value = "image", required = false) MultipartFile multipartFile) {
//    if (multipartFile == null) {
//      // Handle the case when no file is provided
//      return new ResponseEntity<>(new Employee(),HttpStatus.BAD_REQUEST);
//    }
//
//    Employee createdEmployee = employeeService.uploadPassportPic(id, multipartFile);
//    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
//  }
  @GetMapping("/getImage/{id}")
  public ResponseEntity<byte[]> getEmployeeImage(@PathVariable Integer id) {
    Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

    if (employeeOptional.isPresent()) {
      Employee employee = employeeOptional.get();
      byte[] imageBytes = employee.getPassportPic();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_JPEG); // Change to the appropriate media type based on your image format
      headers.setContentLength(imageBytes.length);
      headers.setCacheControl("max-age=2592000"); // Adjust cache control as needed

      return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{empId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer empId, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employee = employeeService.getEmployeeById(empId);
        if (employee.isPresent()) {
            Employee result = employeeService.updateEmployee(empId, updatedEmployee);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer empId) {
        Optional<Employee> employee = employeeService.getEmployeeById(empId);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(empId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/login")
    public ResponseEntity<Employee> login(@RequestParam("name") String name, @RequestParam("pass") String password) {
        return new ResponseEntity<>(employeeService.login(name,password) ,HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,"application/json"},
            produces = {MediaType.APPLICATION_JSON_VALUE ,"application/json"})
    public ResponseEntity<Employee> save(@RequestBody EmployeeDto employee) throws IOException {
        System.out.println(employee);
        Employee empl = employeeService.createEmployee(employee);
        return new ResponseEntity<>(empl,HttpStatus.OK);
    }





//    @PostMapping("/{empId}/upload-passport")
//    public ResponseEntity<Employee> uploadPassportPic(
//            @PathVariable Integer empId,
//            @RequestParam("passportPic") MultipartFile passportPicFile
//    ) {
//        try {
//            Employee updatedEmployee = employeeService.uploadPassportPic(empId, passportPicFile);
//            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
