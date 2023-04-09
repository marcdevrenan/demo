package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.repositories.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        try {
            List<Employee> employeeList = new ArrayList<>();
            employeeRepository.findAll().forEach(employeeList::add);

            if (employeeList.isEmpty()) {
                return new ResponseEntity<>(employeeList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee employeeObj = employeeRepository.save(employee);

        return new ResponseEntity<>(employeeObj, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        Optional<Employee> employeeData = employeeRepository.findById(id);

        if (employeeData.isPresent()) {
            Employee employee = employeeData.get();
            employee.setFirstName(newEmployee.getFirstName());
            employee.setLastName(newEmployee.getLastName());
            employee.setAge(newEmployee.getAge());
            employee.setPosition(newEmployee.getPosition());
            employee.setEmail(newEmployee.getEmail());

            Employee employeeObj = employeeRepository.save(employee);
            return new ResponseEntity<>(employeeObj, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
