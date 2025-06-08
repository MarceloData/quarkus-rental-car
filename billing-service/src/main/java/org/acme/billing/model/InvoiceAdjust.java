package org.acme.billing.model;

import java.time.LocalDate;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class InvoiceAdjust extends PanacheMongoEntity {

    public String rentalId;
    public String userId;
    public LocalDate actualEndDate;
    public double price;
    public boolean paid;

    public InvoiceAdjust(String rentalId, String userId, LocalDate actualEndData, double price) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.actualEndDate = actualEndData;
        this.price = price;
    }

    @Override
    public String toString() {
        return "InvoiceAdjust{" +
                "rentalId='" + rentalId + '\'' +
                ", userId='" + userId + '\'' +
                ", actualEndDate=" + actualEndDate +
                ", price=" + price +
                ", paid=" + paid +
                ", id=" + id +
                '}';
    }
}
