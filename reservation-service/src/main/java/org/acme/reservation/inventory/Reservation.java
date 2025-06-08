// package org.acme.reservation.inventory;

// import java.time.LocalDate;

// import lombok.Data;

// @Data
// public class Reservation {
// private Long id;
// private Long carId;
// private LocalDate startDay;
// private LocalDate endDay;
// private String userId;

// public boolean isReserved(LocalDate starDay, LocalDate endDay) {
// return (!(this.endDay.isBefore(starDay) || this.startDay.isAfter(endDay)));
// }
// }
