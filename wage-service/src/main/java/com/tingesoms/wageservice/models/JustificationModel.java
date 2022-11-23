package com.tingesoms.wageservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JustificationModel {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate justificationDate;

    private String details;
    private Boolean status;
    private String employeeRut;
}
