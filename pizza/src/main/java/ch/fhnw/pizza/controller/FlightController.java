package ch.fhnw.pizza.controller;

import ch.fhnw.pizza.business.service.BookingService;
import ch.fhnw.pizza.business.service.FlightScheduleService;
import ch.fhnw.pizza.data.domain.Airport;
import ch.fhnw.pizza.data.domain.Flight;
import ch.fhnw.pizza.data.dto.BookingRequestDto;
import ch.fhnw.pizza.data.dto.FlightDto;
import ch.fhnw.pizza.data.dto.FlightRequestDto;
import ch.fhnw.pizza.data.projection.BookingProjection;
import ch.fhnw.pizza.data.projection.FlightProjection;
import ch.fhnw.pizza.data.repository.AirportRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/flights")
public class FlightController {

    @Autowired
    private FlightScheduleService flightScheduleService;


    @GetMapping(path="/{flightDesignator}", produces = "application/json")
    public ResponseEntity getFlight(@PathVariable String flightDesignator) {
        try{
            FlightProjection flight = flightScheduleService.findFlightByFlightDesignator(flightDesignator);
            return ResponseEntity.ok(convertToDto(flight));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No flight found with given designator");
        }
    }

    @GetMapping(produces = "application/json")
    public List<FlightDto> getFlightList() {
        List<FlightProjection> flights = flightScheduleService.getAllFlights();
        return flights.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<FlightDto> findFlightsByAirportsAndDate(
            @RequestParam(value = "departureAirportIataCode", required = false) String departureAirportIataCode,
            @RequestParam(value = "arrivalAirportIataCode", required = false) String arrivalAirportIataCode,
            @RequestParam(value = "flightDate", required = false) LocalDate flightDate) {
        List<FlightProjection> flights;

        if (departureAirportIataCode == null && arrivalAirportIataCode == null && flightDate == null) {
            flights = flightScheduleService.getAllFlights();
        } else {
            Airport departureAirport = departureAirportIataCode == null || departureAirportIataCode.isBlank()  ? null : flightScheduleService.findAirportByIataCode(departureAirportIataCode);
            Airport arrivalAirport = arrivalAirportIataCode == null || arrivalAirportIataCode.isBlank() ? null : flightScheduleService.findAirportByIataCode(arrivalAirportIataCode);
            flights = flightScheduleService.findFlightsByAirportsAndDate(departureAirport, arrivalAirport, flightDate);
        }
        return flights.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }


    @PostMapping(consumes="application/json", produces = "application/json")
    public ResponseEntity addFlight(@RequestBody FlightRequestDto flightRequest) {
        FlightProjection flightProjection = null;
        try{
            flightProjection = flightScheduleService.addFlight(flightRequest);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking already exists with given details");
        }
        return ResponseEntity.ok(convertToDto(flightProjection));
    }


    @PutMapping(path="/{id}", consumes="application/json", produces = "application/json")
    public ResponseEntity updatePizza(@PathVariable Long id, @RequestBody FlightRequestDto flightRequest) {
        FlightProjection flightProjection = null;
        try{
            flightProjection = flightScheduleService.updateFlight(id, flightRequest);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking already exists with given details");

        }
        return ResponseEntity.ok(flightProjection);
        
    }

    @GetMapping(path="/airport/{iataCode}", produces = "application/json")
    public ResponseEntity getAirport(@PathVariable String iataCode) {
        try{
            Airport airport = flightScheduleService.findAirportByIataCode(iataCode);
            return ResponseEntity.ok(airport);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No airport found with given IATA code.");
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        try{
            flightScheduleService.deleteFlight(id);
            return ResponseEntity.ok("Flight with id " + id + " deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight not found");
        }
    }
    
    private FlightDto convertToDto(FlightProjection flight) {
        ModelMapper modelMapper = new ModelMapper();
        FlightDto dto = modelMapper.map(flight, FlightDto.class);
        return dto;
    }   


}
