package org.acme.reservation.inventory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.acme.reservation.billing.Invoice;
import org.acme.reservation.entity.Reservation;
import org.acme.reservation.rental.RentalClient;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("reservation")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    private final InventoryClient inventoryClient;
    private final RentalClient rentalClient;
    public static final double STANDARD_RATE_PER_DAY = 19.99;

    @Inject
    @Channel("invoices")
    MutinyEmitter<Invoice> invoiceEmitter;

    @Inject
    SecurityContext context;

    public ReservationResource(@GraphQLClient("inventory") GraphQLInventoryClient inventoryClient,
            @RestClient RentalClient rentalClient) {
        this.inventoryClient = inventoryClient;
        this.rentalClient = rentalClient;
    }

    @GET
    @Path("availability")
    public Uni<Collection<Car>> availability(@RestQuery LocalDate startDate, @RestQuery LocalDate endDate) {
        Uni<List<Car>> availableCarsUni = inventoryClient.allCars();

        Uni<List<Reservation>> reservationsUni = Reservation.listAll();

        return Uni.combine().all().unis(availableCarsUni, reservationsUni).with((availableCars, reservations) -> {
            Map<Long, Car> carsById = new HashMap<>();
            for (Car car : availableCars) {
                carsById.put(car.getId(), car);
            }
            for (Reservation reservation : reservations) {
                if (reservation.isReserved(startDate, endDate)) {
                    carsById.remove(reservation.carId);
                }
            }
            return carsById.values();
        });
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @WithTransaction
    public Uni<Reservation> make(Reservation reservation) {
        reservation.userId = context.getUserPrincipal() != null ? context.getUserPrincipal().getName() : "anonymous";
        return reservation.<Reservation>persist().onItem().call(persistedReservation -> {
            Log.info("Successfully reserved reservation " + persistedReservation);
            Uni<Void> invoiceUni = invoiceEmitter.send(new Invoice(reservation, computePrice(reservation))).onFailure()
                    .invoke(throwable -> Log.errorf("Couldn't create invoice for %s. %s%n", persistedReservation,
                            throwable.getMessage()));
            if (persistedReservation.startDay.equals(LocalDate.now())) {
                return invoiceUni
                        .chain(() -> rentalClient.start(persistedReservation.userId, persistedReservation.id).onItem()
                                .invoke(rental -> Log.info("Successfully started rental " + rental))
                                .replaceWith(persistedReservation));
            }
            return invoiceUni.replaceWith(persistedReservation);
        });

    }

    @GET
    @Path("all")
    public Uni<Collection<Reservation>> allReservations() {
        String userId = context.getUserPrincipal() != null ? context.getUserPrincipal().getName() : null;
        return Reservation.<Reservation>listAll().onItem().transform(reservations -> reservations.stream()
                .filter(reservation -> userId == null || userId.equals(reservation.userId))
                .collect(Collectors.toList()));
    }

    private double computePrice(Reservation reservation) {
        return (ChronoUnit.DAYS.between(reservation.startDay, reservation.endDay) + 1) * STANDARD_RATE_PER_DAY;
    }
}
