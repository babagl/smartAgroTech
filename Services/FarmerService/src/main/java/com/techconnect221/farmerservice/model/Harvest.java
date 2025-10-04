package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "harvests")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {

    @Id
    private UUID id = UUID.randomUUID();
    private Double quantity;      // quantité récoltée
    private String unit;          // tonnes, sacs
    private String quality;       // qualité (ex: A, B, bio)
    private LocalDate harvestDate;
    private Boolean published = false; // si mise en vente sur la plateforme

    @ManyToOne
    @JoinColumn(name = "crop_cycle_id", nullable = false)
    private CropCycle cropCycle;
}
