package org.acme.users.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Reservation {

    private Long id;
    private String userId;
    private Long carId;
    private LocalDate startDay;
    private LocalDate endDay;
}
