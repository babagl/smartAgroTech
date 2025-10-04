package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sub_fields")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubField {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;   // ex: "Zone Nord", "Zone Sud"

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Geometry geometry; // géométrie de la sous-parcelle

    private Double area;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field; // lien vers le champ parent

    @OneToMany(mappedBy = "subField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CropCycle> cropCycles = new ArrayList<>();
}
