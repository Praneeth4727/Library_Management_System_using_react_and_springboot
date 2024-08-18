package com.bibliotheca.spring_boot_library.requestmodels;

import lombok.Data; // Importing Lombok's Data annotation for automatic generation of getters, setters, and other utility methods

/**
 * Request model for payment information.
 * This class is used to encapsulate the data required to create a payment intent
 * when processing a payment through the payment gateway.
 */
@Data // Lombok annotation to automatically generate getters, setters, and toString, equals, and hashCode methods
public class PaymentInfoRequest {

    private int amount; // The amount to be charged, typically in the smallest currency unit (e.g., cents for USD)
    private String currency; // The currency in which the payment is to be made (e.g., "usd", "eur")
    private String receiptEmail; // The email address to which the payment receipt will be sent
}
