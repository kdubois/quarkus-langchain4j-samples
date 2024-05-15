package com.redhat.developers;

import jakarta.inject.Singleton;

import java.time.LocalDate;

import dev.langchain4j.agent.tool.Tool;

@Singleton
public class BookingTools {

    private final BookingService bookingService;

    public BookingTools(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Tool
    public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
        return bookingService.getBookingDetails(bookingNumber, customerName, customerSurname);
    }

    @Tool
    public void cancelBooking(String bookingNumber, String customerName, String customerSurname, LocalDate bookingFrom) {
        bookingService.cancelBooking(bookingNumber, customerName, customerSurname, bookingFrom);
    }
}