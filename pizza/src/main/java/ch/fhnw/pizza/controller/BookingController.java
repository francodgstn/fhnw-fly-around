package ch.fhnw.pizza.controller;

import ch.fhnw.pizza.business.service.BookingService;
import ch.fhnw.pizza.data.dto.BookingRequestDto;
import ch.fhnw.pizza.data.projection.BookingProjection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @GetMapping(path="/booking/{id}", produces = "application/json")
    public ResponseEntity getBooking(@PathVariable Long id) {
        try{
            BookingProjection booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(booking);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking found with given id");
        }
    }
    
    @GetMapping(produces = "application/json")
    public List<BookingProjection> getBookingList(@RequestParam(value = "userEmail", required = false) String userEmail) {
        
        if (userEmail == null || userEmail.isBlank()) {
            return bookingService.getAllBookings();
        }
        else {
            return bookingService.getAllUserBookings(userEmail);
        }
    }

    
    @PostMapping(consumes="application/json", produces = "application/json")
    public ResponseEntity addBooking(@RequestBody BookingRequestDto bookingRequest) {
        BookingProjection bookingProjection = null;
        try{
            bookingProjection  = bookingService.addBooking(bookingRequest);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Booking already exists with given details");
        }
        return ResponseEntity.ok(bookingProjection);
    }


    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        try{
            bookingService.deleteBooking(id);
            return ResponseEntity.ok("Booking with id " + id + " deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
        }
    }
    

}
