package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.models.JustificationModel;
import com.tingesoms.wageservice.models.WorkedDayModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JustificationService {
    @Autowired
    RestTemplate restTemplate;

    public JustificationModel[] getJustificationsByRut(String rut) {
        return restTemplate.getForObject("http://justification-service/justification/" + rut, JustificationModel[].class);
    }
}
