package com.alex.gordon.car.repo;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@RedisHash("Appointment")
public class AppointmentEntity {

    @Indexed
    private String id;
    private Date appointmentDate;
    @Indexed
    private AppointmentStatus appointmentStatus;
    @Indexed
    @NotBlank
    private String client;
    @NotBlank
    private String car;
    @NotBlank
    private String description;
    @NotNull
    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentEntity)) {
            return false;
        }
        AppointmentEntity that = (AppointmentEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AppointmentEntity{" +
            "id='" + id + '\'' +
            ", appointmentDate=" + appointmentDate +
            ", appointmentStatus=" + appointmentStatus +
            ", client='" + client + '\'' +
            ", car='" + car + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            '}';
    }

}
