package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.models.ApprovalModel;
import com.tingesoms.wageservice.models.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    RestTemplate restTemplate;

    public EmployeeModel[] getAllEmployees() {
        return restTemplate.getForObject("http://employee-service/employee/", EmployeeModel[].class);
    }

    public EmployeeModel employeeByRut(String rut){
        return restTemplate.getForObject("http://employee-service/employee/" + rut, EmployeeModel.class);
    }
}
