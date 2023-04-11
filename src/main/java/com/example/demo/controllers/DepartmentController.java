package com.example.demo.controllers;

import com.example.demo.models.Department;
import com.example.demo.repositories.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/[department]")
public class DepartmentController {

    @Autowired
    private IDepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        try {
            List<Department> departmentList = new ArrayList<>();
            departmentRepository.findAll().forEach(departmentList::add);

            if (departmentList.isEmpty()) {
                return new ResponseEntity<>(departmentList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        Optional<Department> departmentsData = departmentRepository.findById(id);

        if (departmentsData.isPresent()) {
            return new ResponseEntity<>(departmentsData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department departmentObj = departmentRepository.save(department);

        return new ResponseEntity<>(departmentObj, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department newDepartment) {
        Optional<Department> departmentData = departmentRepository.findById(id);

        if (departmentData.isPresent()) {
            Department department = departmentData.get();
            department.setName(newDepartment.getName());
            department.setDescription(newDepartment.getDescription());
            department.setPhoneNumber(newDepartment.getPhoneNumber());

            Department departmentObj = departmentRepository.save(department);
            return new ResponseEntity<>(departmentObj, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable Long id) {
        departmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
