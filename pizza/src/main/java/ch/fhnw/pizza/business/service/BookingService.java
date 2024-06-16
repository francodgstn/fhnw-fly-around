package ch.fhnw.pizza.business.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.fhnw.pizza.data.domain.Airport;
import ch.fhnw.pizza.data.domain.Booking;
import ch.fhnw.pizza.data.domain.Flight;
import ch.fhnw.pizza.data.domain.Passenger;
import ch.fhnw.pizza.data.dto.BookingRequestDto;
import ch.fhnw.pizza.data.projection.BookingProjection;
import ch.fhnw.pizza.data.projection.FlightProjection;
import ch.fhnw.pizza.data.repository.AirportRepository;
import ch.fhnw.pizza.data.repository.BookingRepository;
import ch.fhnw.pizza.data.repository.FlightRepository;
import ch.fhnw.pizza.data.repository.PassengerRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    
    @Autowired
    private FlightRepository flightRepository;

    public BookingProjection addBooking(BookingRequestDto bookingRequest) throws Exception {
        // if (booking.getId() != null && bookingRepository.existsById(booking.getId())) {
        //     throw new Exception("Booking with id " + booking.getId() + " already exists");
        // }

        Booking booking = new Booking();
        Long flightId = bookingRequest.getFlightId();
        String paxEmail = bookingRequest.getPassengerEmail();

        booking.setUserEmail(bookingRequest.getUserEmail());

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new Exception("Flight with id " + flightId + " not found"));

        if(paxEmail != null) {
            if (bookingRepository.existsByPassengerEmailAndFlightId(paxEmail, flightId)){
                throw new Exception("Booking already exists with given details");
            }
            
            Passenger pax = new Passenger();
            pax.setEmail(paxEmail);
            pax.setFirstName(bookingRequest.getPassengerFirstName());
            pax.setLastName(bookingRequest.getPassengerLastName());
            pax = getPassengerByEmailOrAdd(pax);
            booking.setPassenger(pax); // set the passenger to the booking    
        }

        booking.setFlight(flight); // set the flight to the 
        booking.setPrice(flight.getPrice());
        booking.setBookingDate(LocalDate.now());

        Booking savedBooking = bookingRepository.save(booking);
        return getBookingById(savedBooking.getId());
    }

    public Passenger addPassenger(Passenger passenger) throws Exception {
        if (passenger.getId() != null && passengerRepository.existsById(passenger.getId())) {
            throw new Exception("Passenger with id " + passenger.getId() + " already exists");
        }
        return passengerRepository.save(passenger);
    }

    public Passenger getPassengerByEmailOrAdd(Passenger passenger) throws Exception {
        if (passenger.getEmail() == null ) {
            throw new Exception("Passenger email required");
        }

        Passenger existingPassenger = passengerRepository.findByEmail(passenger.getEmail());
        
        if (existingPassenger != null) {
            return existingPassenger;
        }
        
        return addPassenger(passenger);
    }
    

    public List<BookingProjection> getAllBookings() {
        List<BookingProjection> bookingList = bookingRepository.findAllProjectedBy();
        return bookingList;
    }


    public List<BookingProjection> getAllUserBookings(String userEmail) {
        List<BookingProjection> bookingList;
        if (userEmail == null || userEmail.isBlank()) {
            return null;
        }
        bookingList = bookingRepository.findAllProjectedByUserEmail(userEmail);
        return bookingList;
    }

    public BookingProjection getBookingById(Long id) {
        try {
            BookingProjection booking = bookingRepository.findProjectedById(id).orElseThrow(() -> new RuntimeException("Booking with id " + id + " not found"));
            return booking;
        } catch (Exception e) {
            throw new RuntimeException("Booking with id " + id + " not found");
        }
    }

    public void deleteBooking(Long id) throws Exception {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        } else {
            throw new Exception("Booking with id " + id + " does not exist");
        }
    }
}
