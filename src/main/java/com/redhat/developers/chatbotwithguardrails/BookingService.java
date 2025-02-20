package com.redhat.developers.chatbotwithguardrails;

import java.time.LocalDate;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookingService {

    @ConfigProperty(name="booking.daystostart")
    int daystostart;

    @ConfigProperty(name="booking.daystoend")
    int daystoend;

    private static String FIRSTNAME="john";
    private static String LASTNAME="doe";
    private static String BOOKINGNUMBER ="123-456";

    public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);
        LocalDate bookingFrom = LocalDate.now().plusDays(daystostart);
        LocalDate bookingTo = LocalDate.now().plusDays(daystoend);
        // Retrieval from DB mocking        
        Customer customer = new Customer(customerName, customerSurname);
        return new Booking(bookingNumber, bookingFrom, bookingTo, customer);
    }

    public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);
        // pretend like we're cancelling the booking
        Log.info("Booking cancelled for user " + customerName + " " + customerSurname);
    }

    private void ensureExists(String bookingNumber, String customerName, String customerSurname) {
        // Check mocking
        if (!(bookingNumber.equals(BOOKINGNUMBER)
                && customerName.toLowerCase().equals(FIRSTNAME)
                && customerSurname.toLowerCase().equals(LASTNAME))) {
            throw new BookingNotFoundException(bookingNumber);
        }
    }
}

class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String bookingNumber) {
        super("Booking " + bookingNumber + " not found");
    }
}

class BookingCannotBeCancelledException extends RuntimeException {

    public BookingCannotBeCancelledException(String bookingNumber) {
        super("Booking " + bookingNumber + " cannot be canceled");
    }
}