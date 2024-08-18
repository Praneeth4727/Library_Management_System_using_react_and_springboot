package com.bibliotheca.spring_boot_library.service;

import java.util.ArrayList; // Importing for using ArrayList
import java.util.HashMap; // Importing for using HashMap
import java.util.List; // Importing for using List
import java.util.Map; // Importing for using Map

import com.bibliotheca.spring_boot_library.dao.PaymentRepository; // Importing PaymentRepository for managing payments
import com.bibliotheca.spring_boot_library.entity.Payment; // Importing Payment entity
import com.bibliotheca.spring_boot_library.requestmodels.PaymentInfoRequest; // Importing request model for payment information
import com.stripe.Stripe; // Importing Stripe for Stripe API integration
import com.stripe.exception.StripeException; // Importing StripeException for handling exceptions
import com.stripe.model.PaymentIntent; // Importing PaymentIntent for creating payment intents

import org.springframework.beans.factory.annotation.Value; // Importing for reading application properties
import org.springframework.http.HttpStatus; // Importing for HTTP status codes
import org.springframework.http.ResponseEntity; // Importing for creating HTTP response entities
import org.springframework.stereotype.Service; // Importing Service to define a service class
import org.springframework.transaction.annotation.Transactional; // Importing Transactional for managing transactions

@Service // Marking this class as a service component
@Transactional // Enabling transaction management for the class
public class PaymentService {
	
	private PaymentRepository paymentRepository; // Repository for payment database operations
	
	// Constructor-based dependency injection for the PaymentRepository and initializing Stripe API key
	public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
		this.paymentRepository = paymentRepository; // Initializing paymentRepository
		Stripe.apiKey = secretKey; // Setting the Stripe API key from application properties
	}
	
	/**
	 * Creates a payment intent for processing payments.
	 * 
	 * @param paymentInfoRequest The request object containing payment details.
	 * @return The created PaymentIntent object.
	 * @throws StripeException if there is an error while creating the payment intent.
	 */
	public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
		List<String> paymentMethodTypes = new ArrayList<>(); // List to hold payment method types
		paymentMethodTypes.add("card"); // Adding card as a payment method type
		
		// Creating a map to hold payment parameters
		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentInfoRequest.getAmount()); // Setting the payment amount
		params.put("currency", paymentInfoRequest.getCurrency()); // Setting the currency for the payment
		params.put("payment_method_types", paymentMethodTypes); // Setting the allowed payment method types
		
		// Creating and returning the payment intent using the Stripe API
		return PaymentIntent.create(params);
	}
	
	/**
	 * Completes the Stripe payment for a user.
	 * 
	 * @param userEmail The email of the user making the payment.
	 * @return ResponseEntity indicating the status of the payment process.
	 * @throws Exception if payment information is missing.
	 */
	public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
		// Fetching payment information for the user
		Payment payment = paymentRepository.findByUserEmail(userEmail);
		
		// Checking if payment information is available
		if(payment == null) {
			throw new Exception("Payment information is missing..!"); // Throwing exception if payment is not found
		}
		payment.setAmount(00.00); // Resetting the payment amount to zero
		paymentRepository.save(payment); // Saving the updated payment information to the database
		
		// Returning an HTTP 200 OK response
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
