package ch.fhnw.pizza.data.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "flight")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Hidden //This annotation hides the id field from the swagger documentation
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "flight_designator")
    private String flightDesignator;

    @Temporal(TemporalType.DATE)
    @Column(name = "flight_date")
    private LocalDate  flightDate;

    @ManyToOne
    @JoinColumn(name = "departure_airport_fk")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_fk")
    private Airport arrivalAirport;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_date_time_local")
    private LocalDateTime  departureDateTimeLocal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_date_time_local")
    private LocalDateTime  arrivalDateTimeLocal;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @Column(name = "price")
    private double price;

    // Getter and Setter methods for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter methods for flightDesignator
    public String getFlightDesignator() {
        return flightDesignator;
    }

    public void setFlightDesignator(String flightDesignator) {
        this.flightDesignator = flightDesignator;
    }

    // Getter and Setter methods for flightDate
    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    // Getter and Setter methods for departureAirport
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    // Getter and Setter methods for arrivalAirport
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }


    // Getter and Setter methods for bookings
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    // Getter and Setter methods for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

     // Getter and Setter methods for departureDateTimeLocal
     public LocalDateTime getDepartureDateTimeLocal() {
        return departureDateTimeLocal;
    }

    public void setDepartureDateTimeLocal(LocalDateTime departureDateTimeLocal) {
        this.departureDateTimeLocal = departureDateTimeLocal;
    }

    // Getter and Setter methods for arrivalDateTimeLocal
    public LocalDateTime getArrivalDateTimeLocal() {
        return arrivalDateTimeLocal;
    }

    public void setArrivalDateTimeLocal(LocalDateTime arrivalDateTimeLocal) {
        this.arrivalDateTimeLocal = arrivalDateTimeLocal;
    }
}
