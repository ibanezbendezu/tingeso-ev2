package com.tingesoms.wageservice.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "wage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToMany(mappedBy = "wage", fetch = FetchType.EAGER)
    private List<WageDetailEntity> detail;

    private String employeeRut;
}
