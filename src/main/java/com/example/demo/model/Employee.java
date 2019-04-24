package com.example.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Employee {
    @Id
    private String employeeId;
    private String email;
    private String fullName;
}
