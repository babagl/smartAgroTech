package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "crop_cycles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CropCycle {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "sub_field_id", nullable = false) // ✅ maintenant lié à SubField
    private SubField subField;

    private String cropName;
    private LocalDate plantingDate;
    private LocalDate expectedHarvestDate;
    private LocalDate actualHarvestDate;

    private Double yieldEstimation;
    private Double yieldActual;
    private String healthStatus;

    @OneToMany(mappedBy = "cropCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seed> seeds = new ArrayList<>();

    @OneToMany(mappedBy = "cropCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Harvest> harvests = new ArrayList<>();

    @OneToMany(mappedBy = "cropCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldMonitoring> monitoring = new ArrayList<>();
}
