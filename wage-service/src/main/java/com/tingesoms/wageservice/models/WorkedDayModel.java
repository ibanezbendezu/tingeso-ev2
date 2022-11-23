package com.tingesoms.wageservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkedDayModel {
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private Double overtime;
    private Long minutesLate;

}
