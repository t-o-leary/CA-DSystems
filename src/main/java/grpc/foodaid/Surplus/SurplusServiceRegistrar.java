/*------------------------------------------------------------------------------------------------------------------
 * SurplusServiceRegistrar class
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
 * This class registers the Surplus gRPC service on the local network using jmDNS for service discovery. 
 * It creates a jmDNS instance, registers the service with a specified name and port, logs the registration status, 
 * and keeps the service available for discovery for a set duration. 
 *  
------------------------------------------------------------------------------------------------------------------*/
package grpc.foodaid.Surplus;

// Import libraries
import java.io.IOException; // Import IOException for handling input/output exceptions
import java.net.InetAddress;// Import InetAddress for network address handling
import java.util.logging.Logger;// Import Logger for logging events

import javax.jmdns.JmDNS;// Import JmDNS for service discovery
import javax.jmdns.ServiceInfo;// Import ServiceInfo for service registration

// This code is adapted from https://github.com/jmdns/jmdns
public class SurplusServiceRegistrar {

	public static void main(String[] args) throws InterruptedException {// Main method to start the service registrar
		JmDNS jmdns = null;
		try {

			jmdns = JmDNS.create(InetAddress.getLocalHost());// Create a JmDNS instance using the local host
																// address
			Logger.getLogger(SurplusServiceRegistrar.class.getName()).info("JmDNS instance created"); // Log the
																										// creation of
																										// JmDNS
																										// instance

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", "SurplusService", 50051,
					"path=index.html");// Create a ServiceInfo object with the service type, name, port, and additional
										// properties
			jmdns.registerService(serviceInfo);
			Logger.getLogger(SurplusServiceRegistrar.class.getName()).info("SurplusService registered with jmDNS");// Log
																													// the
																													// service
																													// registration

			// Wait a bit
			Thread.sleep(60000);// Sleep for 60 seconds to keep the service available for discovery

		} catch (IOException e) {
			Logger.getLogger(SurplusServiceRegistrar.class.getName())
					.severe("Error creating JmDNS instance: " + e.getMessage());// Log any IOException that occurs
		} catch (InterruptedException e) {
			Logger.getLogger(SurplusServiceRegistrar.class.getName())
					.severe("Interrupted while waiting: " + e.getMessage());// Log any InterruptedException that occurs
		} finally {// Ensure resources are cleaned up
			if (jmdns != null) {// Check if JmDNS instance is not null
				try {
					jmdns.close();// Close the JmDNS instance to release resources
					Logger.getLogger(SurplusServiceRegistrar.class.getName()).info("JmDNS instance closed");// Log the
																											// closure
																											// of JmDNS
																											// instance
				} catch (IOException e) {// Handle any IOException that occurs during closure
					Logger.getLogger(SurplusServiceRegistrar.class.getName())// Log the error message
							.severe("Error closing JmDNS: " + e.getMessage());
				}
			}
		}
	}
}
