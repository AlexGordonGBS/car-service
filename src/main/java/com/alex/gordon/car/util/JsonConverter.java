package com.alex.gordon.car.util;

import com.alex.gordon.car.repo.AppointmentEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final AppointmentEntity entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AppointmentEntity asEntity(final String json) {
        try {
            return mapper.readValue(json, AppointmentEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
