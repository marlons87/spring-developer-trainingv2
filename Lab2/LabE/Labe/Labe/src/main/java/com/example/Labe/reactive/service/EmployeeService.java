package com.example.Labe.reactive.service;

import com.example.Labe.reactive.model.Employee;
import com.example.Labe.reactive.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void create(Employee employee) {
        employeeRepository.save(employee);
    }

    public Mono<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Flux<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Flux<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Mono<Employee> update(Employee e) {
        return employeeRepository.save(e);
    }

    public Mono<Void> deleteById(Integer id){
        return employeeRepository.deleteById(id);
    }
}
