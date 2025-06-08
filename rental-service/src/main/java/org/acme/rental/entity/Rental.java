package org.acme.rental.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "Rentals")
public class Rental extends PanacheMongoEntity {

    public boolean paid;
    public String userId;
    public Long reservationId;
    public LocalDate startDate;
    public LocalDate endDate;

    @BsonProperty("rental-service")
    public boolean active;

    @Override
    public String toString() {
        return "Rental{" +
                "userId='" + userId + '\'' +
                ", reservationId=" + reservationId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                ", id=" + id +
                '}';
    }

    public static Optional<Rental> findByUserAndReservationIdsOptional(String userId, Long reservationId) {
        return find("userId=?1 and reservatioId=?2", userId, reservationId).firstResultOptional();
    }

    public static List<Rental> listActive() {
        return list("active", true);
    }
}
