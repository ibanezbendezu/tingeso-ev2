package com.tingesoms.employeeservice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tingesoms.employeeservice.repositories.entities.EmployeeEntity;
import com.tingesoms.employeeservice.services.EmployeeService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class EmployeeRest {

    private final EmployeeService employeeService;
    public EmployeeRest(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping
    //@RolesAllowed("admin")
    public ResponseEntity<List<EmployeeEntity>> getEmployees(){
        List<EmployeeEntity> employees = employeeService.getEmployees();
        if(employees.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{rut}")
    //@RolesAllowed("admin")
    public ResponseEntity<EmployeeEntity> getByRut(@PathVariable("rut") String rut){
        EmployeeEntity employee = employeeService.getByRut(rut);
        if(employee == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    //@RolesAllowed("admin")
    public void saveEmployee(@RequestBody EmployeeEntity employee){
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/delete")
    public void deleteEmployees(){
        employeeService.deleteEmployees();
    }
}
