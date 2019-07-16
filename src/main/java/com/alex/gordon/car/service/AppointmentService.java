package com.alex.gordon.car.service;

import com.alex.gordon.car.repo.AppointmentEntity;
import com.alex.gordon.car.repo.AppointmentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);
    private AppointmentRepo appointmentRepo;

    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public Optional<AppointmentEntity> getById(String id) {
        String methodName = "getById";
        LOGGER.info(String.format("Method=%s | id=%s", methodName, id));
        Optional<AppointmentEntity> entity = appointmentRepo.findById(id);
        LOGGER.info(String.format("Method=%s | entity=%s", methodName, entity));
        return entity;
    }

    public AppointmentEntity addAppointment(AppointmentEntity appointmentEntity) {
        String methodName = "addAppointment";
        LOGGER.info(String.format("Method=%s | Entity=%s", methodName, appointmentEntity));
        String id = UUID.randomUUID().toString();
        appointmentEntity.setId(id);
        AppointmentEntity newEntity = appointmentRepo.save(appointmentEntity);
        LOGGER.info(String.format("Method=%s | New Entity ADDED=%s", methodName, newEntity));
        return newEntity;
    }

    public void deleteById(String id) {
        String methodName = "deleteById";
        LOGGER.info(String.format("Method=%s | id=%s", methodName, id));
        appointmentRepo.deleteById(id);
        LOGGER.info(String.format("Method=%s | entity with id=%s has been deleted", methodName, id));
    }

    public AppointmentEntity updateAppointment(String id, AppointmentEntity appointmentEntity) {
        String methodName = "updateAppointment";
        LOGGER.info(String.format("Method=%s | RequestEntity=%s", methodName, appointmentEntity));
        AppointmentEntity originalEntity = appointmentRepo.findById(id).orElse(null);
        LOGGER.info(String.format("Method=%s | OriginalEntity=%s", methodName, originalEntity));
        if (originalEntity == null) {
            return null;
        } else {
            appointmentEntity.setId(id);
            AppointmentEntity updatedTimesheet = appointmentRepo.save(appointmentEntity);
            LOGGER.info(String.format("Method=%s | UpdatedEntity=%s", methodName, updatedTimesheet));
            return updatedTimesheet;
        }
    }

    public List<AppointmentEntity> getByDateRange(Date fromDate, Date toDate) {
        return appointmentRepo.findAllByAppointmentDateBetween(fromDate, toDate);
    }

}
