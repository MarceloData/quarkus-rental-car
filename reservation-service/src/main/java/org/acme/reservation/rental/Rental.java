package org.acme.reservation.rental;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rental {

    private final String id;
    private final String userId;
    private final Long reservationId;
    private final LocalDate startDate;

    @Override
    public String toString() {
        return "Rental(" +
                "id=" + id +
                ", userId=" + userId + '\'' +
                ", reservationId=" + reservationId +
                ", startDate=" + startDate +
                '}';
    }
}
