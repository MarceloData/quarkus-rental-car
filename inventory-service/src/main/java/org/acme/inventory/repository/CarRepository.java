package org.acme.inventory.repository;

import java.util.Optional;

import org.acme.inventory.model.Car;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

    public Optional<Car> findByLicencePlateNumberOptional(String licencePlateNumber) {
        return find("licencePlateNumber", licencePlateNumber).firstResultOptional();
    }
}
