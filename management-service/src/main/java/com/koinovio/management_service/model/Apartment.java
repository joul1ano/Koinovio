package com.koinovio.management_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "apartments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "floor")
    private Integer floor; //floor level

    @Column(name = "size")
    private Double size; //in sq meters

    @OneToOne(mappedBy = "apartment")
    private Tenant tenant;
}
