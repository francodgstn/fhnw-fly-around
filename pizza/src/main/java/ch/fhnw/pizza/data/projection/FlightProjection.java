package ch.fhnw.pizza.data.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FlightProjection {
    Long getId();
    String getFlightDesignator();
    LocalDate getFlightDate();
    LocalDateTime  getDepartureDateTimeLocal();
    LocalDateTime  getArrivalDateTimeLocal();
    double getPrice();
    DepartureAirportProjection getDepartureAirport();
    ArrivalAirportProjection getArrivalAirport();

    interface DepartureAirportProjection {
        String getIataCode();
        String getIcaoCode();
        String getName();
    }

    interface ArrivalAirportProjection {
        String getIataCode();
        String getIcaoCode();
        String getName();
    }
}
