package com.bibliotheca.spring_boot_library.utils;

import java.util.Base64; // Importing Base64 for decoding
import java.util.HashMap; // Importing HashMap for storing key-value pairs
import java.util.Map; // Importing Map interface

public class ExtractJWT {
	
	/**
	 * Extracts a specific value from the JWT token payload.
	 * 
	 * @param token The JWT token from which the value is to be extracted.
	 * @param extraction The key for the value that needs to be extracted from the payload.
	 * @return The extracted value as a String, or null if the key is not found.
	 */
	public static String payloadJWTExtraction(String token, String extraction) {
		// Remove "Bearer" from the token if present
		token = token.replace("Bearer ", ""); // Fixed replacement with space to avoid token issues
		
		// Split the token into its three parts (header, payload, signature)
		String[] chunks = token.split("\\.");
		
		// Create a Base64 decoder for URL-encoded strings
		Base64.Decoder decoder = Base64.getUrlDecoder();
		
		// Decode the payload (second chunk of the token)
		String payload = new String(decoder.decode(chunks[1]));
		
		// Split the payload into key-value pairs
		String[] entries = payload.split(",");
		
		// Create a map to hold the extracted key-value pairs
		Map<String, String> map = new HashMap<String, String>();
		
		// Iterate through each entry in the payload
		for(String entry : entries) {
			// Split each entry into key and value
			String[] keyValue = entry.split(":");
			
			// Check if the current key matches the key to extract
			if(keyValue[0].equals(extraction)) {
				// Adjust the value string to remove extra characters
				int remove = 1; // Default removal is 1 character
				if(keyValue[1].endsWith("}")) {
					remove = 2; // If the value ends with '}', remove 2 characters
				}
				
				// Trim the value string to remove surrounding quotes and extra characters
				keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
				keyValue[1] = keyValue[1].substring(1); // Remove leading quote

				// Put the key-value pair into the map
				map.put(keyValue[0], keyValue[1]);
			}
		}
		
		// Return the extracted value if the map contains the key
		if(map.containsKey(extraction)) {
			return map.get(extraction); // Return the extracted value
		}
		
		// Return null if the key was not found
		return null;	
	}
}
