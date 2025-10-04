package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "seeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seed {

    @Id
    private UUID id = UUID.randomUUID();

    private String variety;     // variété (ex: hybride X12)
    private String supplier;    // fournisseur
    private Double quantity;    // quantité utilisée
    private String unit;        // kg, sacs
    private Boolean certified = false;

    @ManyToOne
    @JoinColumn(name = "crop_cycle_id", nullable = false)
    private CropCycle cropCycle;
}