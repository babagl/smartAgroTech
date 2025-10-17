package com.techconnect221.farmerservice.service.domain;

import com.techconnect221.farmerservice.dto.FieldDTO;
import com.techconnect221.farmerservice.model.Field;

import java.util.UUID;

public interface FieldService {
    Field findById(UUID id);
    Field findFieldByyFarmer(UUID farmerId);
    Field createField(FieldDTO f);
    String getFieldAsGeojson(UUID id);
}
