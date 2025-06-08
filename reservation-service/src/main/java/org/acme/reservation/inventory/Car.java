package org.acme.reservation.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long id;
    private String licensePlateNumber;
    private String manufacturer;
    private String model;
}
