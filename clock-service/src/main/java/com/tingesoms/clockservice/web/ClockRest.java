package com.tingesoms.clockservice.web;
import com.tingesoms.clockservice.repositories.entities.WorkedDayEntity;
import com.tingesoms.clockservice.services.ClockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/clock")
public class ClockRest {
    private final ClockService clockService;
    public ClockRest(ClockService clockService) {
        this.clockService = clockService;
    }

    @PutMapping("/save")
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        if (this.clockService.loadClock(file)) {
            return new ResponseEntity<>("Clock saved.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Clock not saved.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<WorkedDayEntity>> getDaysByRut(@PathVariable String rut) {
        List<WorkedDayEntity> days = clockService.daysByRut(rut);
        return ResponseEntity.ok(days);
    }
}
