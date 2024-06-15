package ch.fhnw.pizza.data.projection;

import java.util.Date;

public interface BookingProjection {
    Long getId();
    Date getCheckinDate();
    PassengerProjection getPassenger();
    FlightProjection getFlight();
    String getUserEmail();

    
    interface PassengerProjection {
        String getFirstName();
        String getLastName();
        String getEmail();
    }
}