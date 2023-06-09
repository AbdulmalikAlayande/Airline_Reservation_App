package AirLineReservationTest.ServicesTest;

import data.model.Flight;
import data.model.Payment;
import data.model.PaymentMethod;
import data.model.Price;
import dtos.Request.BookingRequest;
import dtos.Request.PassengerRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Bookable;
import services.FlightBooking;
import services.PassengerService;
import services.PassengerServiceImplementation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookableTest {
	Bookable bookable;
	BookingRequest bookingRequest;
	Payment payment;
	PassengerRequest passengerRequest;
	PassengerService passengerService;
	Flight booked;
	
	@BeforeEach void startAllTestWith(){
		payment = new Payment();
		passengerService = PassengerServiceImplementation.getInstance();
		bookable = new FlightBooking();
		bookingRequest = new BookingRequest();
		passengerRequest = new PassengerRequest();
	}
	
	@Test void checkAvailableFlightTest(){
		Bookable bookable1 = new FlightBooking();
		Flight availableFlight = bookable1.checkAvailableFlight();
		assertNotNull(availableFlight);
	}
	@SneakyThrows
	@Test void testThatPassengerCanBookFlight_AndSeatsWillBeAssignedToThePassenger(){
		passengerService.registerNewPassenger(buildPassenger());
		bookingRequest.setBookingCategory(3);
		bookingRequest.setPassengerUsername(buildPassenger().getUserName());
		booked = bookable.bookFlight(bookingRequest);
		assertTrue(booked.getSeats()[15]);
	}
	
	@SneakyThrows
	@Test void testThatAFlightHasToBeFullyBookedBeforeAnother(){
		passengerService.registerNewPassenger(buildPassenger());
		passengerService.registerNewPassenger(buildPassenger1());
		
		Flight bookedFlight = bookable.bookFlight(getBookingRequest1());
		Flight bookedFlight2 = bookable.bookFlight(getBookingRequest2());
		assertTrue(bookedFlight.getSeats()[10]);
		assertTrue(bookedFlight2.getSeats()[15]);
		System.out.println(booked);
	}
	
	
	@Test void bookFlight_AddPassengerToFlightListOfPassengersTest(){
	
	}
	
	@Test void bookFlight_GenerateFlightFormTest(){
	
	}
	
	private PassengerRequest buildPassenger(){
		return PassengerRequest.builder()
				       .userName("abdul@20")
				       .Email("alaabdulmalik03@gmail.com")
				       .phoneNumber("07036174617")
				       .firstName("Abdulmalik")
				       .lastName("Alayande")
				       .password("ayanniyi@20")
				       .build();
	}
	
	private PassengerRequest buildPassenger1(){
		return PassengerRequest.builder()
				       .userName("crayon")
				       .Email("alaabdulmalik03@gmail.com")
				       .phoneNumber("07036174617")
				       .firstName("pencil")
				       .lastName("eraser")
				       .password("ayanniyi@20")
				       .build();
	}
	@NotNull
	private BookingRequest getBookingRequest2(){
		Payment payment2 = new Payment();
		payment2.setPaymentMethod(PaymentMethod.CARD);
		payment2.setPrice(Price.ECONOMY_CLASS);
		payment2.setStatus(true);
		
		BookingRequest bookingRequest2 = new BookingRequest();
		bookingRequest2.setBookingCategory(3);
		bookingRequest2.setPayment(payment2);
		bookingRequest2.setPassengerUsername(buildPassenger1().getUserName());
		return bookingRequest2;
	}
	
	private BookingRequest getBookingRequest1() {
		Payment payment1 = new Payment();
		payment1.setPaymentMethod(PaymentMethod.CASH);
		payment1.setPrice(Price.BUSINESS_CLASS);
		payment1.setStatus(true);
		
		BookingRequest bookingRequest1 = new BookingRequest();
		bookingRequest1.setBookingCategory(2);
		bookingRequest1.setPayment(payment1);
		bookingRequest1.setPassengerUsername(buildPassenger().getUserName());
		return bookingRequest1;
	}
}