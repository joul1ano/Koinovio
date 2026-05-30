package com.koinovio.management_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tenants")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "apartment_id", unique = true)
    private Apartment apartment;

    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;
}
