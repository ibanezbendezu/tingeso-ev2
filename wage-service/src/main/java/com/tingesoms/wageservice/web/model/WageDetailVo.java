package com.tingesoms.wageservice.web.model;

import com.tingesoms.wageservice.entities.enums.DetailType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WageDetailVo {
    private Long id;
    private String name;
    private DetailType type;
    private BigDecimal amount;
}
