package com.alex.gordon.car.controller;

import com.alex.gordon.car.repo.AppointmentEntity;
import com.alex.gordon.car.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p> Controller class.</p>
 *
 * @author Alex Gordon
 */
@RestController
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Returns ONE appointment by ID
     *
     * @return list of appointments
     */
    @GetMapping(value = "/appointments/{id}", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> getById(@Valid @PathVariable String id) {
        AppointmentEntity entity = appointmentService.getById(id).orElse(null);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete one appointment by ID.
     * If appointment is NOT found then completes successfully.
     *
     * @param id - appointment ID
     * @return void
     */
    @DeleteMapping(value = "/appointments/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        appointmentService.deleteById(id);
        // DELETE operation is expected to be idempotent, therefore deleting an already deleted item is the successful operation!!!
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Create one new appointment.
     *
     * @param appointmentEntity - appointment entity object - payload
     * @return a copy of the newly created appointment with the generated ID and all other fields.
     */
    @PostMapping(value = "/appointments", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> addAppointment(@Valid @RequestBody AppointmentEntity appointmentEntity) {
        return new ResponseEntity<>(appointmentService.addAppointment(appointmentEntity), HttpStatus.CREATED);
    }

    /**
     * Updates an existing appointment.
     * All the fields are required.
     *
     * @param id                - mandatory
     * @param appointmentEntity - payload
     * @return updated appointment
     */
    @PutMapping(value = "/appointments/{id}", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> updateAppointment(@PathVariable String id, @Valid @RequestBody AppointmentEntity appointmentEntity) {
        AppointmentEntity entity = appointmentService.updateAppointment(id, appointmentEntity);
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns ALL appointment for the specific date range sorted by price
     *
     * @param fromDate - FROM date for the date range.
     * @param toDate   - TO date for the date range.
     * @return list of appointments
     */
    @GetMapping(value = "/appointments/range/{fromDate}/{toDate}", produces = {"application/json"})
    public ResponseEntity<List<AppointmentEntity>> getByDateRange(@NotBlank @PathVariable String fromDate, @NotBlank @PathVariable String toDate) {
        return new ResponseEntity<>(appointmentService.getByDateRange(fromDate, toDate), HttpStatus.OK);
    }

}
