package com.techconnect221.farmerservice.service.domain.impl;

import com.techconnect221.farmerservice.dto.FieldDTO;
import com.techconnect221.farmerservice.model.Field;
import com.techconnect221.farmerservice.service.domain.FieldService;

import java.util.UUID;

public class FieldServiceImpl implements FieldService {

    @Override
    public Field findById(UUID id) {
        return null;
    }

    @Override
    public Field findFieldByyFarmer(UUID farmerId) {
        return null;
    }

    @Override
    public Field createField(FieldDTO f) {
        return null;
    }

    @Override
    public String getFieldAsGeojson(UUID id) {
        return "";
    }
}
