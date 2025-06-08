package org.acme.reservation.entity;

import java.time.LocalDate;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Reservation extends PanacheEntity {

    public Long carId;
    public String userId;
    public LocalDate startDay;
    public LocalDate endDay;

    public boolean isReserved(LocalDate startDay, LocalDate endDay) {
        return (!(this.endDay.isBefore(startDay) || this.startDay.isAfter(endDay)));
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", userId='" + userId + '\'' + ", startDay=" + startDay + ", endDay="
                + endDay + "}";
    }
}
