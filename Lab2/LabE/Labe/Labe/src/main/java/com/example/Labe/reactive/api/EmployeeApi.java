package com.example.Labe.reactive.api;

import com.example.Labe.reactive.model.Employee;
import com.example.Labe.reactive.service.EmployeeService;
import com.example.Labe.springData.dto.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/employee")
public class EmployeeApi {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Employee employee){
        employeeService.create(employee);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Mono<Employee>> findById(@PathVariable ("id") Integer id){
        Mono<Employee> employeeMono = employeeService.findById(id);
        return new ResponseEntity<Mono<Employee>>(employeeMono, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public Flux<Employee> findByName(@PathVariable("name") String name){
        return employeeService.findByName(name);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Employee> update(@RequestBody Employee employee){
        return employeeService.update(employee);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Integer id){
        employeeService.deleteById(id);
    }
}
