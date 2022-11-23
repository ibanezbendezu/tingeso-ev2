package com.tingesoms.wageservice.web.model;

import com.tingesoms.wageservice.models.EmployeeModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WageVo {
    private Long id;
    private LocalDate date;
    private List<WageDetailVo> detail;
    private EmployeeModel employee;
}
