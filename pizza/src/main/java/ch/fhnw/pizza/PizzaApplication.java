package ch.fhnw.pizza;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fhnw.pizza.business.service.BookingService;
import ch.fhnw.pizza.business.service.FlightScheduleService;
import ch.fhnw.pizza.business.service.MenuService;
import ch.fhnw.pizza.data.domain.Airport;
import ch.fhnw.pizza.data.domain.Booking;
import ch.fhnw.pizza.data.domain.Flight;
import ch.fhnw.pizza.data.domain.Passenger;
import ch.fhnw.pizza.data.domain.Pizza;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@RestController
@Hidden // Hide this controller from the Swagger UI
public class PizzaApplication {

	@Autowired
	private MenuService menuService;
	@Autowired
	private FlightScheduleService flightScheduleService;
	@Autowired
	private BookingService bookingService;

	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}
	

	// Use this method to initialize placeholder data without using Postman
	// If you are persisting data in a file (see application.properties), initializing data that already exists will cause an error during starting the application
	// To resolve the error, delete the file and restart the application
	@PostConstruct
	private void initPlaceholderData() throws Exception {
		Pizza pizza = new Pizza();
		pizza.setPizzaName("Margherita");
		pizza.setPizzaToppings("Tomato sauce, mozzarella, basil");
		menuService.addPizza(pizza);

		pizza = new Pizza();
		pizza.setPizzaName("Funghi");
		pizza.setPizzaToppings("Tomato sauce, mozzarella, mushrooms");
		menuService.addPizza(pizza);

		// // Rome
		// Destination destinationRome = new Destination();
		// destinationRome.setName("Rome");
		// destinationRome.setDescription("The capital of Italy");
		// destinationRome.setTags(List.of("City", "Capital", "Italy"));
		// destinationRome.setAirports(List.of(airportFco, airportCia));
		// destinationRome = flightScheduleService.addDestination(destinationRome);
		// // Zurich
		// Destination destinationZurich = new Destination();
		// destinationZurich.setName("Zurich");
		// destinationZurich.setDescription("Most poulate city in Switzerland");
		// destinationZurich.setTags(List.of("City", "Switzerland"));
		// destinationZurich = flightScheduleService.addDestination(destinationZurich);

	    // FCO
		Airport airportFco = new Airport();
		airportFco.setName("Rome-Fiumicino International Airport");
		airportFco.setIataCode("FCO");
		airportFco.setIcaoCode("LIRF");
		airportFco.setCity("Rome");
		airportFco = flightScheduleService.addAirport(airportFco);
		// airportFco.setDestinations(List.of(destinationRome));
		
		// CIA
		Airport airportCia = new Airport();
		airportCia.setName("Rome-Ciampino International Airport");
		airportCia.setIataCode("CIA");
		airportCia.setIcaoCode("LIRA");
		airportCia.setCity("Rome");
		airportCia = flightScheduleService.addAirport(airportCia);
		//airportCia.setDestinations(List.of(destinationRome));

		//ZRH
		Airport airportZrh = new Airport();
		airportZrh.setName("Zurich Airport");
		airportZrh.setIataCode("ZRH");
		airportZrh.setIcaoCode("LSZH");
		airportZrh.setCity("Zurich");
		airportZrh = flightScheduleService.addAirport(airportZrh);
		//airportZrh.setDestinations(List.of(destinationZurich));

		// LHR
		Airport airportLhr = new Airport();
		airportLhr.setName("London Heathrow Airport");
		airportLhr.setIataCode("LHR");
		airportLhr.setIcaoCode("EGLL");
		airportLhr.setCity("London");
		airportLhr = flightScheduleService.addAirport(airportLhr);
		//airportLhr.setDestinations(List.of(destinationLondon));

		// FRA
		Airport airportFra = new Airport();
		airportFra.setName("Frankfurt Airport");
		airportFra.setIataCode("FRA");
		airportFra.setIcaoCode("EDDF");
		airportFra.setCity("Frankfurt");
		airportFra = flightScheduleService.addAirport(airportFra);
		//airportFra.setDestinations(List.of(destinationFrankfurt));
		
		
		String[] flightDesignators = {"LX", "BA", "LH", "AF"};
		Random random = new Random();
		List<Airport> airports = flightScheduleService.getAllAirports();
		
		Flight flight;
		for (int i = 0; i < 15; i++) {	
			flight = new Flight();
			// Randomize a bit the fligts data
			String randomFlightDesignator = flightDesignators[random.nextInt(flightDesignators.length)] + String.format("%03d", random.nextInt(1000));
			int daysToAdd = i % 3 + 1;
			int randomHour = new Random().nextInt(20);
			int randomHourArrival = randomHour + new Random().nextInt(3);
			int randomMinute = new Random().nextInt(12) * 5;
			int randomMinuteArrival = new Random().nextInt(12) * 5;

			flight.setFlightDesignator(randomFlightDesignator);
			flight.setFlightDate(LocalDate.now().plusDays(daysToAdd));
			flight.setDepartureDateTimeLocal(LocalDateTime.now().plusDays(daysToAdd).withHour(randomHour).withMinute(randomMinute));
			flight.setArrivalDateTimeLocal(LocalDateTime.now().plusDays(daysToAdd).withHour(randomHourArrival).withMinute(randomMinuteArrival));
			// Set random departure and arrival airports
			int departureIndex = new Random().nextInt(airports.size());
			int arrivalIndex = new Random().nextInt(airports.size());
			Airport departureAirport = airports.get(departureIndex);
			Airport arrivalAirport = airports.get(arrivalIndex);
			
			// Make sure departure and arrival airports are different
			while (departureAirport.equals(arrivalAirport)) {
				arrivalIndex = new Random().nextInt(airports.size());
				arrivalAirport = airports.get(arrivalIndex);
			}
			flight.setDepartureAirport(departureAirport);
			flight.setArrivalAirport(arrivalAirport);
			flight = flightScheduleService.addFlight(flight);
		}


		// Create 3 sample passengers
		Passenger passenger1 = new Passenger();
		passenger1.setFirstName("John");
		passenger1.setLastName("Doe");
		passenger1.setEmail("john.doe@example.com");
		passenger1 = bookingService.addPassenger(passenger1);


		Passenger passenger2 = new Passenger();
		passenger2.setFirstName("Jane");
		passenger2.setLastName("Smith");
		passenger2.setEmail("jane.smith@example.com");
		passenger2 = bookingService.addPassenger(passenger2);

		Passenger passenger3 = new Passenger();
		passenger3.setFirstName("Mike");
		passenger3.setLastName("Johnson");
		passenger3.setEmail("mike.johnson@example.com");
		passenger3 = bookingService.addPassenger(passenger3);


		// Create a booking
		// Booking booking = new Booking();
		
		// booking.setCheckinDate(LocalDate.now());
		// bookingService.addBooking(booking, passenger2, flight.getId());

	}

}
