package com.redhat.developers.chatbotwithguadrails;

import java.time.LocalDate;

public record Booking(String bookingNumber, LocalDate bookingFrom, LocalDate bookingTo, Customer customer) {}