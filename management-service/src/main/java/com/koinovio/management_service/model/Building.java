package com.koinovio.management_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "number_of_floors")
    private Integer floors;

    @Column(name = "last_expense_submission")
    private LocalDate lastExpenseSubmission;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    List<Apartment> apartmentsList = new ArrayList<>();
}
