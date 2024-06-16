package ch.fhnw.pizza.data.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import ch.fhnw.pizza.data.domain.Flight;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class FlightRequestDto {
    private String flightDesignator;
    private LocalDate flightDate;
    private LocalDateTime departureDateTimeLocal;
    private LocalDateTime arrivalDateTimeLocal;
    private String departureAirportIataCode;
    private String arrivalAirportIataCode;
    private double price;

    public String getFlightDesignator() {
        return flightDesignator;
    }

    public void setFlightDesignator(String flightDesignator) {
        this.flightDesignator = flightDesignator;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public String getDepartureAirportIataCode() {
        return departureAirportIataCode;
    }

    public void setDepartureAirportIataCode(String departureAirportIataCode) {
        this.departureAirportIataCode = departureAirportIataCode;
    }

    public String getArrivalAirportIataCode() {
        return arrivalAirportIataCode;
    }

    public void setArrivalAirportIataCode(String arrivalAirportIataCode) {
        this.arrivalAirportIataCode = arrivalAirportIataCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDepartureDateTimeLocal() {
        return departureDateTimeLocal;
    }

    public void setDepartureDateTimeLocal(LocalDateTime departureDateTimeLocal) {
        this.departureDateTimeLocal = departureDateTimeLocal;
    }

    public LocalDateTime getArrivalDateTimeLocal() {
        return arrivalDateTimeLocal;
    }

    public void setArrivalDateTimeLocal(LocalDateTime arrivalDateTimeLocal) {
        this.arrivalDateTimeLocal = arrivalDateTimeLocal;
    }

}
