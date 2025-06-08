package org.acme.users.model;

import lombok.Data;

@Data
public class Car {

    private Long id;
    private String licensePlateNumber;
    private String manufacturer;
    private String model;
}
