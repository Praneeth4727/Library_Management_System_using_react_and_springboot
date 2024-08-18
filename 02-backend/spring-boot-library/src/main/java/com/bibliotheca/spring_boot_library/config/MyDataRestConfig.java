package com.bibliotheca.spring_boot_library.config;

import com.bibliotheca.spring_boot_library.entity.Book; // Importing the Book entity
import com.bibliotheca.spring_boot_library.entity.Message; // Importing the Message entity
import com.bibliotheca.spring_boot_library.entity.Review; // Importing the Review entity

import org.springframework.context.annotation.Configuration; // Importing the Configuration annotation
import org.springframework.data.rest.core.config.RepositoryRestConfiguration; // Importing RepositoryRestConfiguration for REST configuration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer; // Importing RepositoryRestConfigurer interface for configuring Spring Data REST
import org.springframework.http.HttpMethod; // Importing HttpMethod for HTTP method configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Importing CorsRegistry for CORS configuration

@Configuration // Annotation indicating that this class provides Spring configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	private String theAllowedOrigins = "https://localhost:3000"; // Allowed origins for CORS

	// Configuring repository REST settings
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// Defining unsupported HTTP methods for specific entities
		HttpMethod[] theUnsupportedActions = {
				HttpMethod.PUT, 
				HttpMethod.POST, 
				HttpMethod.DELETE, 
				HttpMethod.PATCH};
		
		// Exposing entity IDs in the response
		config.exposeIdsFor(Book.class);
		config.exposeIdsFor(Review.class);
		config.exposeIdsFor(Message.class);
		
		// Disabling unsupported HTTP methods for each entity
		disableHttpMethods(Book.class, config, theUnsupportedActions);
		disableHttpMethods(Review.class, config, theUnsupportedActions);
		disableHttpMethods(Message.class, config, theUnsupportedActions);
		
		/* Configure CORS Mapping */
		cors.addMapping(config.getBasePath() + "/**") // Adding mapping for all paths
		.allowedOrigins(theAllowedOrigins); // Allowing specified origins
	}

	// Method to disable unsupported HTTP methods for given entity
	private void disableHttpMethods(Class theClass, 
			RepositoryRestConfiguration config,
			HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration()
		 .forDomainType(theClass) // Targeting the specified domain type
		 .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)) // Disabling actions for single items
		 .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions)); // Disabling actions for collections
	}
}
