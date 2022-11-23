package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.models.ApprovalModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApprovalService {
    @Autowired
    RestTemplate restTemplate;

    public ApprovalModel[] getApprovalsByRut(String rut) {
        return restTemplate.getForObject("http://approval-service/approval/" + rut, ApprovalModel[].class);
    }
}
