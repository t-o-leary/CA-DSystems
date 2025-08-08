/*------------------------------------------------------------------------------------------------------------------
 * DeliverServiceRegistrar class
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
 * This class registers the Delivery gRPC service on the local network using jmDNS for service discovery. 
 * It creates a jmDNS instance, registers the service with a specified name and port, logs the registration status, 
 * and keeps the service available for discovery for a set duration. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package grpc.foodaid.Deliver;

// Import necessary libraries
import java.io.IOException;// Import the IOException class for handling input/output exceptions
import java.net.InetAddress;// Import the InetAddress class for network
import java.util.logging.Level;// Import the Level class for logging
import java.util.logging.Logger;// Import the JmDNS library for service discovery

import javax.jmdns.JmDNS;//	 Import the JmDNS class for registering services on the local network
import javax.jmdns.ServiceInfo;// Import the ServiceInfo class for service

public class DeliverServiceRegistrar {

	public static void main(String[] args) throws InterruptedException {
		JmDNS jmdns = null;// Declare a JmDNS instance for service discovery
		try {
			jmdns = JmDNS.create(InetAddress.getLocalHost());// Create a JmDNS instance using the local host's IP
																// address
			Logger.getLogger(DeliverServiceRegistrar.class.getName()).info("jmDNS instance created successfully");// Log
																													// the
																													// successful
																													// creation
																													// of
																													// the
																													// JmDNS
																													// instance

			ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", "DeliverService", 500053, // Define the
																										// service type
																										// and name
					"path=index.html");
			jmdns.registerService(serviceInfo);// Register the DeliverService with jmDNS
			Logger.getLogger(DeliverServiceRegistrar.class.getName()).info("DeliverService registered with jmDNS");// Log
																													// the
																													// successful
																													// registration
																													// of
																													// the
																													// service

			Thread.sleep(60000);// Keep the service available for discovery for 60 seconds

		} catch (IOException e) {
			Logger.getLogger(DeliverServiceRegistrar.class.getName()).log(Level.SEVERE,
					"jmDNS registration failed: " + e.getMessage(), e);// Log any IOException that occurs during service
																		// registration
		} catch (Exception e) {
			Logger.getLogger(DeliverServiceRegistrar.class.getName()).log(Level.SEVERE,
					"Unexpected error: " + e.getMessage(), e);// Log any unexpected exceptions that occur

		} finally {
			if (jmdns != null) {
				try {
					jmdns.unregisterAllServices();// Unregister all services from jmDNS
					jmdns.close();// Close the jmDNS instance to release resources
				} catch (IOException e) {
					Logger.getLogger(DeliverServiceRegistrar.class.getName()).log(Level.WARNING,
							"Error closing jmDNS: " + e.getMessage(), e);// Log any IOException that occurs while
																			// closing jmDNS
				}
			}
		}
	}
}