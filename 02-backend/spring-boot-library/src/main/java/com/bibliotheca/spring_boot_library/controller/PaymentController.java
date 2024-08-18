package com.bibliotheca.spring_boot_library.controller;

import com.bibliotheca.spring_boot_library.requestmodels.PaymentInfoRequest; // Importing the request model for payment information
import com.bibliotheca.spring_boot_library.service.PaymentService; // Importing the service for handling payment operations
import com.bibliotheca.spring_boot_library.utils.ExtractJWT; // Importing JWT extraction utility
import com.stripe.exception.StripeException; // Importing exception class for handling Stripe errors
import com.stripe.model.PaymentIntent; // Importing Stripe PaymentIntent model

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired annotation for dependency injection
import org.springframework.http.HttpStatus; // Importing HttpStatus for response status
import org.springframework.http.ResponseEntity; // Importing ResponseEntity for HTTP responses
import org.springframework.web.bind.annotation.CrossOrigin; // Importing CrossOrigin annotation for CORS configuration
import org.springframework.web.bind.annotation.PostMapping; // Importing PostMapping annotation for handling POST requests
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping annotation for handling PUT requests
import org.springframework.web.bind.annotation.RequestBody; // Importing RequestBody annotation for accessing request bodies
import org.springframework.web.bind.annotation.RequestHeader; // Importing RequestHeader annotation for accessing HTTP headers
import org.springframework.web.bind.annotation.RequestMapping; // Importing RequestMapping annotation for defining request mappings
import org.springframework.web.bind.annotation.RestController; // Importing RestController annotation to define RESTful controller

@CrossOrigin("https://localhost:3000") // Allowing CORS for the specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/payment/secure") // Base URL mapping for secure payment-related operations
public class PaymentController {

    private PaymentService paymentService; // Service for handling payment-related logic

    // Constructor for dependency injection of the payment service
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Endpoint for creating a payment intent with Stripe
    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {
        // Creating a PaymentIntent using the provided payment information
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        // Converting the PaymentIntent to a JSON string for response
        String paymentStr = paymentIntent.toJson();

        // Returning the PaymentIntent JSON string with an OK status
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    // Endpoint for completing a Stripe payment
    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
            throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Checking if the user email was successfully extracted
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        // Calling the service to complete the payment and returning the response
        return paymentService.stripePayment(userEmail);
    }
}
