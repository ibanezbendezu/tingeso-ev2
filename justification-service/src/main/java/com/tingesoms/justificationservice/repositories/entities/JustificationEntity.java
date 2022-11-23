package com.tingesoms.justificationservice.repositories.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "justification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JustificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate justificationDate;

    private String details;
    private Boolean status;
    private String employeeRut;
}
