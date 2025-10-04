package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "field_monitoring")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class FieldMonitoring {

    @Id
    private UUID id = UUID.randomUUID();

    private String actionType;    // irrigation, fertilisation, pesticide
    private String description;   // détails
    private LocalDateTime date;   // date de l’action

    @ManyToOne
    @JoinColumn(name = "crop_cycle_id", nullable = false)
    private CropCycle cropCycle;
}
