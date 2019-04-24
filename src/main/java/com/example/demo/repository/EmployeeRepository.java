package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByEmployeeId(String employeeId, Pageable pageable);
    List<Employee> findByFullName(String fullName, Pageable pageable);
    List<Employee> findByEmail(String email, Pageable pageable);
}
