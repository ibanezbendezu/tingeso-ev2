package com.tingesoms.approvalservice.services;

import com.tingesoms.approvalservice.models.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
    @Autowired
    RestTemplate restTemplate;

    public EmployeeModel employeeByRut(String rut){
        return restTemplate.getForObject("http://employee-service/employee/" + rut, EmployeeModel.class);
    }
}
