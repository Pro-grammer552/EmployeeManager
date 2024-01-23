package com.task.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@JsonDeserialize
public class EmployeeDto {
    private String empName;
    private String pass;
    private Integer roleId;
    private Integer deptId;

   private String image;

}
