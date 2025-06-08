package org.acme.rental.reservation;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8081")
public interface ReservationClient {

    @GET
    @Path("/admin/reservation/{id}")
    Reservation getById(Long id);
}
