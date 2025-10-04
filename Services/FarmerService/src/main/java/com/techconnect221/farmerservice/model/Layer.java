package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "layers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Layer {
    @Id
    private UUID id = UUID.randomUUID();
    private String layerName;
    private String layerType;
    private Boolean visible = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id", nullable = false)
    private Map map;
    @OneToMany(mappedBy = "layer",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Legend> legends;

    @ManyToMany
    @JoinTable(
            name = "layer_fields",
            joinColumns = @JoinColumn(name = "layer_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    private List<Field> fields;

}
