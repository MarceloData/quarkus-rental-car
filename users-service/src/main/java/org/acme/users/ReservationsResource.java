package org.acme.users;

import java.time.LocalDate;
import java.util.Collection;

import org.acme.users.model.Car;
import org.acme.users.model.Reservation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/")
public class ReservationsResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index(
                LocalDate startDate,
                LocalDate endDate,
                String name);

        public static native TemplateInstance listofreservations(Collection<Reservation> reservations);

        public static native TemplateInstance availablecars(Collection<Car> cars, LocalDate startDate,
                LocalDate endDate);
    }

    @Inject
    SecurityContext securityContext;

    @Inject
    @RestClient
    ReservationsClient client;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index(@RestQuery LocalDate startDate, @RestQuery LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1L);
        }
        if (endDate == null) {
            endDate = LocalDate.now().plusDays(7);
        }
        return Templates.index(startDate, endDate, securityContext.getUserPrincipal().getName());
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/get")
    public TemplateInstance getReservations() {
        Collection<Reservation> reservationColletion = client.allReservations();
        return Templates.listofreservations(reservationColletion);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/available")
    public TemplateInstance getAvailableCars(@RestQuery LocalDate startDate, @RestQuery LocalDate endDate) {
        Collection<Car> availaCars = client.availability(startDate, endDate);
        return Templates.availablecars(availaCars, startDate, endDate);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/reserve")
    public RestResponse<TemplateInstance> create(
            @RestForm LocalDate startDate, @RestForm LocalDate endDate, @RestForm Long carId) {
        Reservation reservation = new Reservation();
        reservation.setStartDay(startDate);
        reservation.setEndDay(endDate);
        reservation.setCarId(carId);
        client.make(reservation);
        return RestResponse.ResponseBuilder.ok(getReservations())
                .header("HX-Trigger-After-Swap", "update-available-cars-list").build();
    }
}
