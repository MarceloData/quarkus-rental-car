package org.acme.rental.reservation;

import java.time.LocalDate;

public class Reservation {

    public Long carId;
    public String userId;
    public LocalDate startDay;
    public LocalDate endDay;

    public boolean isReserved(LocalDate startDay, LocalDate endDay) {
        return (!(this.endDay.isBefore(startDay) || this.startDay.isAfter(endDay)));
    }

    @Override
    public String toString() {
        return "Reservation{" + "userId='" + userId + '\'' + ", startDay=" + startDay + ", endDay="
                + endDay + "}";
    }
}
