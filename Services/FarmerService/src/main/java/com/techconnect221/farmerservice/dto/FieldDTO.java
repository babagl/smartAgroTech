package com.techconnect221.farmerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDTO {

    private UUID id;
    private String fieldName;
    private String goeJson;
    private Double area;
    private UUID farmerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
