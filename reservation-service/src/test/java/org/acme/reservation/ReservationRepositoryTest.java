// package org.acme.reservation;

// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;

// import org.acme.reservation.inventory.Reservation;
// import org.acme.reservation.inventory.ReservationRepository;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;

// import io.quarkus.test.junit.QuarkusTest;
// import jakarta.inject.Inject;

// @QuarkusTest
// public class ReservationRTest {

// @Inject
// ReservationRepository reservationRepository;

// @Test
// public void testCreateReservation() {
// Reservation reservation = new Reservation();
// reservation.setStartDay(LocalDate.now().plus(5, ChronoUnit.DAYS));
// reservation.setEndDay(LocalDate.now().plus(12, ChronoUnit.DAYS));
// reservation.setCarId(384L);
// reservationRepository.save(reservation);

// Assertions.assertNotNull(reservation.getId());
// Assertions.assertTrue(reservationRepository.findAll().contains(reservation));
// }
// }
