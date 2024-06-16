package ch.fhnw.pizza.business.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.pizza.data.domain.Airport;
import ch.fhnw.pizza.data.domain.Destination;
import ch.fhnw.pizza.data.domain.Flight;
import ch.fhnw.pizza.data.domain.Pizza;
import ch.fhnw.pizza.data.dto.FlightRequestDto;
import ch.fhnw.pizza.data.projection.FlightProjection;
import ch.fhnw.pizza.data.repository.AirportRepository;
import ch.fhnw.pizza.data.repository.DestinationRepository;
import ch.fhnw.pizza.data.repository.FlightRepository;

@Service
public class FlightScheduleService {

    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private AirportRepository airportRepository;



    public FlightProjection findFlightById(Long id) {
        try {
            FlightProjection flight = flightRepository.findProjectedById(id).orElseThrow(() -> new RuntimeException("Flight with id " + id + " not found"));
            return flight;
        } catch (Exception e) {
            throw new RuntimeException("Flight with id " + id + " not found");
        }
    }

    public FlightProjection findFlightByFlightDesignator(String flightDesignator) {
        try {
            FlightProjection flight = flightRepository.findProjectedByFlightDesignator(flightDesignator).orElseThrow(() -> new RuntimeException("Flight with designator " + flightDesignator + " not found"));
            return flight;
        } catch (Exception e) {
            throw new RuntimeException("Flight with designator " + flightDesignator + " not found");
        }
    }


    public List<FlightProjection> getAllFlights() {
        List<FlightProjection> flightList = flightRepository.findAllProjectedBy();
        return flightList;
    }

    public Flight addFlight(Flight flight) throws Exception {
        
        if (flight.getId() != null && flightRepository.existsById(flight.getId())) {
            throw new Exception("Flight with id " + flight.getId() + " already exists");
        }
    
        if (flight.getPrice() == 0) { 
            // Add the price to the flight
            double flightTime = ChronoUnit.HOURS.between(flight.getDepartureDateTimeLocal(), flight.getArrivalDateTimeLocal());
            double randomFactor = Math.random() * 0.6 + 1;
            double price = flightTime * 100 * randomFactor;
            double roundedPrice = Math.max(Math.round(price * 100.0) / 100.0, 35.0);

            flight.setPrice(roundedPrice);
        }

        return flightRepository.save(flight);
    }


    public FlightProjection addFlight(FlightRequestDto flightRequest) throws Exception {

        Flight flight = new Flight();
        flight.setFlightDesignator(flightRequest.getFlightDesignator());
        flight.setFlightDate(flightRequest.getDepartureDateTimeLocal().toLocalDate());
        flight.setDepartureDateTimeLocal(flightRequest.getDepartureDateTimeLocal());
        flight.setArrivalDateTimeLocal(flightRequest.getArrivalDateTimeLocal());
        flight.setPrice(flightRequest.getPrice());
        flight.setArrivalDateTimeLocal(flightRequest.getArrivalDateTimeLocal());
        flight.setDepartureDateTimeLocal(flightRequest.getDepartureDateTimeLocal());
        Airport departureAirport = findAirportByIataCode(flightRequest.getDepartureAirportIataCode());
        Airport arrivalAirport = findAirportByIataCode(flightRequest.getArrivalAirportIataCode());
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);       

        flight = addFlight(flight);
        Long id = flight.getId();

        return flightRepository.findProjectedById(id).orElseThrow(() -> new RuntimeException("Flight with id " + id + " not found"));

    }
    

    public List<FlightProjection> findFlightsByAirportsAndDate(Airport departureAirport, Airport arrivalAirport, LocalDate flightDate) {
        return flightRepository.findFlightsByAirportsAndDate(departureAirport, arrivalAirport, flightDate);
    }

    public FlightProjection updateFlight(Long id, FlightRequestDto flightRequest) throws Exception {
       
        Flight flightToUpdate = flightRepository.findById(id).orElseThrow(() -> new Exception("Flight with id " + id + " does not exist"));
    
        flightToUpdate.setFlightDesignator(flightRequest.getFlightDesignator());
        flightToUpdate.setFlightDate(flightRequest.getFlightDate());
        flightToUpdate.setDepartureDateTimeLocal(flightRequest.getDepartureDateTimeLocal());
        flightToUpdate.setArrivalDateTimeLocal(flightRequest.getArrivalDateTimeLocal());
        flightToUpdate.setPrice(flightRequest.getPrice());
        flightToUpdate.setArrivalDateTimeLocal(flightRequest.getArrivalDateTimeLocal());
        flightToUpdate.setDepartureDateTimeLocal(flightRequest.getDepartureDateTimeLocal());
        Airport departureAirport = findAirportByIataCode(flightRequest.getDepartureAirportIataCode());
        Airport arrivalAirport = findAirportByIataCode(flightRequest.getArrivalAirportIataCode());
        flightToUpdate.setDepartureAirport(departureAirport);
        flightToUpdate.setArrivalAirport(arrivalAirport);       
        flightToUpdate = flightRepository.save(flightToUpdate);

        return flightRepository.findProjectedById(id).orElseThrow(() -> new RuntimeException("Flight with id " + id + " not found"));
    }
    
    public List<Airport> getAllAirports() {
        List<Airport> airportList = airportRepository.findAll();
        return airportList;
    }
    
    public Airport findAirportByIataCode(String iataCode) {
        try {
            Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new RuntimeException("Airport with IATA code " + iataCode + " not found"));
            return airport;
        } catch (Exception e) {
            throw new RuntimeException("Airport with IATA code " + iataCode + " not found");
        }
    }

    
    public Airport addAirport(Airport airport) throws Exception {
        if (airport.getIataCode() != null) {
           
            if ( airportRepository.findByIataCode(airport.getIataCode()).isPresent()) {
                throw new RuntimeException("Airport with IATA code " + airport.getIataCode() + " already exists.");
            }
            return airportRepository.save(airport);
        }
        throw new Exception("Invalid airport name");
    }


    public void deleteAirport(Long id) throws Exception {
        if (airportRepository.existsById(id)) {
            airportRepository.deleteById(id);
        } else {
            throw new Exception("Airport with id " + id + " does not exist");
        }
    }

    public void deleteFlight(Long id) throws Exception {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        } else {
            throw new Exception("Flight with id " + id + " does not exist");
        }
    }

}
