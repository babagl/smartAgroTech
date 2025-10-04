package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.geolatte.geom.Geometry;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "maps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Map {

    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String description;

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Geometry geometry;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Layer> layers;
}
