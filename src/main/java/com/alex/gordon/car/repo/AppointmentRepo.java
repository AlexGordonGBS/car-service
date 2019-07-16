package com.alex.gordon.car.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p> Repository interface.</p>
 *
 * @author Alex Gordon
 */
@Repository
public interface AppointmentRepo extends CrudRepository<AppointmentEntity, String> {

    List<AppointmentEntity> findAllByAppointmentDateBetween(Date fromDate, Date toDate);

}

