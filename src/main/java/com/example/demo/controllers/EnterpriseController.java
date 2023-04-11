package com.example.demo.controllers;

import com.example.demo.models.Enterprise;
import com.example.demo.repositories.IEnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enterprise")
public class EnterpriseController {

    @Autowired
    private IEnterpriseRepository enterpriseRepository;

    @GetMapping
    public ResponseEntity<List<Enterprise>> getEnterprises() {
        try {
            List<Enterprise> enterpriseList = new ArrayList<>();
            enterpriseRepository.findAll().forEach(enterpriseList::add);

            if (enterpriseList.isEmpty()) {
                return new ResponseEntity<>(enterpriseList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(enterpriseList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> getEnterprise(@PathVariable Long id) {
        Optional<Enterprise> enterpriseData = enterpriseRepository.findById(id);

        if (enterpriseData.isPresent()) {
            return new ResponseEntity<>(enterpriseData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Enterprise> createEnterprise(@RequestBody Enterprise enterprise) {
        Enterprise enterpriseObj = enterpriseRepository.save(enterprise);

        return new ResponseEntity<>(enterpriseObj, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Enterprise> updateEnterprise(@PathVariable Long id, @RequestBody Enterprise newEnterprise) {
        Optional<Enterprise> enterpriseData = enterpriseRepository.findById(id);

        if (enterpriseData.isPresent()) {
            Enterprise enterprise = enterpriseData.get();
            enterprise.setName(newEnterprise.getName());
            enterprise.setAddress(newEnterprise.getAddress());
            enterprise.setPhoneNumber(newEnterprise.getPhoneNumber());

            Enterprise enterpriseObj = enterpriseRepository.save(enterprise);
            return new ResponseEntity<>(enterpriseObj, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEnterprise(@PathVariable Long id) {
        enterpriseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
