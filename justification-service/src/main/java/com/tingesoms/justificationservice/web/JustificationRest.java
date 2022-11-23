package com.tingesoms.justificationservice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tingesoms.justificationservice.repositories.entities.JustificationEntity;
import com.tingesoms.justificationservice.services.JustificationService;

import java.time.LocalDate;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/justification")
public class JustificationRest {

    private final JustificationService justificationService;

    public JustificationRest(JustificationService justificationService) {
        this.justificationService = justificationService;
    }

    @GetMapping
    public List<JustificationEntity> getJustifications(){
        return justificationService.getJustifications();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<JustificationEntity>> getJustificationsByRut(@PathVariable String rut){
        List<JustificationEntity> justification = justificationService.getJustificationsByRut(rut);
        return ResponseEntity.ok(justification);
    }

    @PostMapping
    public ResponseEntity<JustificationEntity> saveJustification(@RequestBody JustificationEntity justification) {
        return ResponseEntity.ok(justificationService.saveJustification(justification));
    }

    @GetMapping("/delete")
    public void deleteJustifications(){
        justificationService.deleteJustification();
    }
}
