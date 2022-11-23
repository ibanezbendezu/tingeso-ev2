package com.tingesoms.approvalservice.web;

import com.tingesoms.approvalservice.repositories.entities.ApprovalEntity;
import com.tingesoms.approvalservice.services.ApprovalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/approval")
public class ApprovalRest {

    private final ApprovalService approvalService;

    public ApprovalRest(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping
    public List<ApprovalEntity> getApprovals(){
        return approvalService.getApprovals();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<ApprovalEntity>> getApprovalsByRut(@PathVariable String rut){
        List<ApprovalEntity> approval = approvalService.getApprovalsByRut(rut);
        return ResponseEntity.ok(approval);
    }

    @PostMapping
    public ResponseEntity<ApprovalEntity> saveApproval(@RequestBody ApprovalEntity approval) {
        return ResponseEntity.ok(approvalService.saveApproval(approval));
    }

    @GetMapping("/delete")
    public void deleteApprovals(){
        approvalService.deleteApproval();
    }
}
