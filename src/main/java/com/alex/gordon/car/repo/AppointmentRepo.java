package com.alex.gordon.car.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <p> Repository interface.</p>
 *
 * @author Alex Gordon
 */
@Repository
public interface AppointmentRepo extends CrudRepository<AppointmentEntity, String> {

}

