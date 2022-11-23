package com.tingesoms.justificationservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.tingesoms.justificationservice.models.EmployeeModel;

@Service
public class EmployeeService {
    @Autowired
    RestTemplate restTemplate;

    public EmployeeModel employeeByRut(String rut){
        return restTemplate.getForObject("http://employee-service/employee/" + rut, EmployeeModel.class);

        /*
        ResponseEntity<Object> response = restTemplate.getForEntity("http://employee-service/employee/" + rut, Object.class);
        Object record = response.getBody();
        if (record == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(record, EmployeeModel.class);*/
    }
}
