/*------------------------------------------------------------------------------------------------------------------
 * OrderServiceRegistrar class
 * 
 * Author Tim O’Leary
 * Student ID: 23287021 
 * Version 1.0
 * Program: Higher Diploma in Science in Computing
 * Module: Distributed Systems  (HDSDEV_JAN25)
 * Lecturer: Sheresh Zahoor
 * Project: CA
 * Submission Date 08-August-2025
 * 
 * This class registers the Order gRPC service on the local network using jmDNS for service discovery. 
 * It creates a jmDNS instance, registers the service with a specified name and port, logs the registration status, 
 * and keeps the service available for discovery for a set duration. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Order;

// import libraries
import java.io.IOException; // for IOException
import java.net.InetAddress; // for InetAddress
import java.util.logging.Logger; // for logging

import javax.jmdns.JmDNS; // for jmDNS
import javax.jmdns.ServiceInfo; // for ServiceInfo

public class OrderServiceRegistrar {

	private static final Logger logger = Logger.getLogger(OrderServiceRegistrar.class.getName()); // Logger for logging messages

	public static void main(String[] args) {
		JmDNS jmdns = null;// Initialize JmDNS to null
		try {
			// Create a JmDNS instance
			jmdns = JmDNS.create(InetAddress.getLocalHost());// Create JmDNS instance with the local host address
			logger.info("Registration: InetAddress.getLocalHost(): " + InetAddress.getLocalHost()); // Log the local host address

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", "OrderService", 50052, "path=index.html");// Create ServiceInfo with service type, name, port, and additional properties
			jmdns.registerService(serviceInfo);// Register the service with jmDNS
			logger.info("OrderService registered with jmDNS");// Log the successful registration

			
			Thread.sleep(60000);// Sleep for 60 seconds to keep the service available for discovery

		} catch (IOException e) {
			logger.severe("Failed to register OrderService with jmDNS: " + e.getMessage());// Log the IOException if service registration fails
		} catch (InterruptedException e) {
			logger.severe("Service registration interrupted: " + e.getMessage());// Log the InterruptedException if the thread is interrupted
			Thread.currentThread().interrupt();// Restore the interrupted status
		} catch (Exception e) {	
			logger.severe("Unexpected error: " + e.getMessage());// Log any unexpected exceptions
		} finally {
			if (jmdns != null) {// Check if jmdns is not null
				try {
					jmdns.close();// Close the jmDNS instance to release resources
					logger.info("JmDNS instance closed.");//
				} catch (IOException e) {
					logger.severe("Failed to close JmDNS: " + e.getMessage());// Log the IOException if closing jmDNS fails
				}
			}
		}
	}
}