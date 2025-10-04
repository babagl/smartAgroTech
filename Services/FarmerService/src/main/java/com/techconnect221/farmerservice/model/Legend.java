package com.techconnect221.farmerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "legends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Legend {
    @Id
    private UUID legendId = UUID.randomUUID();
    private String attribute;
    private String value;
    private String color;
    private String symbol;
    private boolean visible = true ;
    @ManyToOne
    @JoinColumn(name = "layer_id",nullable = false)
    private Layer layer;

}
