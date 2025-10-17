package com.techconnect221.farmerservice.model;

import com.techconnect221.farmerservice.enums.FieldStatus;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "fields")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Field {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Geometry geometry;
    private Double area;
    private Double perimeter;
    private String soilType;
    private String irrigationType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID farmerId;  // Référence à Auth/User Service
    // ✅ Nouveau : un champ peut avoir plusieurs sous-parcelles
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubField> subFields = new ArrayList<>();

    @ManyToMany(mappedBy = "fields")
    private List<Layer> layers;
}

