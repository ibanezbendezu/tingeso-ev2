package com.tingesoms.wageservice.web;

import com.tingesoms.wageservice.services.HRMService;
import com.tingesoms.wageservice.services.WageService;
import com.tingesoms.wageservice.web.model.WageVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("wage")
public class WageRest {
    private final WageService wageService;
    private final HRMService hrmService;

    public WageRest(WageService wageService, HRMService hrmService) {
        this.wageService = wageService;
        this.hrmService = hrmService;
    }

    @PostMapping
    public ResponseEntity<String> generateWages(){
        try {
            this.hrmService.generateWages();
            return new ResponseEntity<>("Wages generated.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Wages not generated.", HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<List<WageVo>> getWages(){
        List<WageVo> wages = wageService.getWages();
        if(wages.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(wages);
    }
}