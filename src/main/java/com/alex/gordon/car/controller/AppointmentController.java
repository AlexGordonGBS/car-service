package com.alex.gordon.car.controller;

import com.alex.gordon.car.repo.AppointmentEntity;
import com.alex.gordon.car.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
     * Returns ONE appointment by ID the DB.
     *
     * @return list of timesheets
     */
    @GetMapping(value = "/appointments", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> getById() {
        return new ResponseEntity<>(appointmentService.getById(), HttpStatus.OK);
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
        // DELETE operation is expected to be idempotent, therefore delete an already deleted item is operation successful!!!
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Create one new appointment.
     *
     * @param appointmentEntity - appointment entity object - payload
     * @return a copy of the newly created appointment with the generated ID and all other fields.
     */
    @PostMapping(value = "/appointments", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> addTimesheet(@Valid @RequestBody AppointmentEntity appointmentEntity) {
        return new ResponseEntity<>(appointmentService.addAppointment(appointmentService), HttpStatus.CREATED);
    }

    /**
     * Updates an existing appointment.
     * All the fields are required.
     *
     * @param id                - mandatory
     * @param appointmentEntity - payload
     * @return updated timesheet
     */
    @PutMapping(value = "/appointments/{id}", produces = {"application/json"})
    public ResponseEntity<AppointmentEntity> updateTimesheet(@PathVariable String id, @Valid @RequestBody AppointmentEntity appointmentEntity) {
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
     * @return list of timesheets
     */
    @GetMapping(value = "/appointments/range/{fromDate}/{toDate}", produces = {"application/json"})
    public ResponseEntity<List<AppointmentEntity>> getByDateRange(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        return new ResponseEntity<>(appointmentService.getByDateRange(fromDate, toDate), HttpStatus.OK);
    }

}
