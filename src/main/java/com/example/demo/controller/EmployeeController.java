package com.example.demo.controller;

import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/all")
    public Collection<Employee> all(@PageableDefault(page = 0, size = Integer.MAX_VALUE) Pageable pageable) {
        return employeeRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }


    @GetMapping("/find/{id}")
    public Employee findByOne(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        try {
            if (!employee.isPresent())
            throw new StudentNotFoundException("id-" + employeeId);
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
        }

        return employee.get();
    }

    @GetMapping("/find")
    public Collection<Employee> list(@RequestParam(value = "employeeId", defaultValue = "") String employeeId,
                                     @RequestParam(value = "fullName", defaultValue = "") String fullName,
                                     @RequestParam(value = "email", defaultValue = "") String email,
                                     @PageableDefault(page = 0, size = Integer.MAX_VALUE) Pageable pageable) {
        if(!employeeId.isEmpty()) {
            return employeeRepository.findByEmployeeId(employeeId, pageable).stream()
                    .collect(Collectors.toList());
        } else if(!fullName.isEmpty()) {
            return employeeRepository.findByFullName(fullName, pageable).stream()
                    .collect(Collectors.toList());
        } else if(!email.isEmpty()) {
            return employeeRepository.findByEmail(email, pageable).stream()
                    .collect(Collectors.toList());
        }
        return employeeRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public String save(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return "Successfully save employee";
    }

    @DeleteMapping("/delete/{id}")
    @Async
    public CompletableFuture<String> delete(@PathVariable String id) throws InterruptedException {
        employeeRepository.deleteById(id);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture("Successfully delete employee id: " + id);
    }

}
