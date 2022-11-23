package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.models.WorkedDayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WorkedDayService {
    @Autowired
    RestTemplate restTemplate;

    public WorkedDayModel[] getWorkedDaysByRut(String rut) {
        return restTemplate.getForObject("http://clock-service/clock/" + rut, WorkedDayModel[].class);
    }
}
