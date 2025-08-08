/*------------------------------------------------------------------------------------------------------------------
 * Discovery class
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
 * This class demonstrates service discovery using JmDNS in a local network.
 * It listens for gRPC services advertised via mDNS/ and logs service events.
 * The implementation requires one Discovery method to discover all services.
 *  
------------------------------------------------------------------------------------------------------------------*/

package Discovery;
// Import Libraries 
import java.io.IOException;// Import IOException for handling I/O errors
import java.net.InetAddress;// Import InetAddress for local host address
import java.net.UnknownHostException;//     JmDNS library for service discovery
import java.util.logging.Logger;// Import Logger for logging messages

import javax.jmdns.JmDNS;// JmDNS library for service discovery
import javax.jmdns.ServiceEvent;//	 JmDNS service event for handling service events

import javax.jmdns.ServiceListener;// JmDNS service listener for discovering services

public class Discovery {

	public static void main(String[] args) {
		JmDNS jmdns = null;
		try {
			jmdns = JmDNS.create(InetAddress.getLocalHost());// Get the local host address
			jmdns.addServiceListener("_grpc._tcp.local.", new ServiceListener() {// Register a service listener
				@Override
				public void serviceAdded(ServiceEvent event) {
					Logger.getLogger(Discovery.class.getName()).info("Service added: " + event.getName());// Log the service addition
				}

				@Override
				public void serviceRemoved(ServiceEvent event) {
					Logger.getLogger(Discovery.class.getName()).info("Service removed: " + event.getName());//	 Log the service removal
				}

				@Override
				public void serviceResolved(ServiceEvent event) {
					Logger.getLogger(Discovery.class.getName()).info("Service resolved: " + event.getInfo());//	 Log the service resolution
				}
			});
			Thread.sleep(60000);// Wait for 60 seconds to allow service discovery
		} catch (UnknownHostException e) {
			Logger.getLogger(Discovery.class.getName()) 
					.severe("Could not determine local host address: " + e.getMessage());// Log the error
			
		} catch (IOException e) {
			Logger.getLogger(Discovery.class.getName()).severe("I/O error: " + e.getMessage());// Log I/O errors
			System.err.println("I/O error occurred while discovering services.");
		} catch (InterruptedException e) {
			Logger.getLogger(Discovery.class.getName()).severe("Thread interrupted: " + e.getMessage());// Log thread interruption
			System.err.println("Operation interrupted.");
			Thread.currentThread().interrupt();
		} finally {
			if (jmdns != null) {// Close JmDNS if it was created
				try {
					jmdns.close();// Close the JmDNS instance
				} catch (IOException e) {
					Logger.getLogger(Discovery.class.getName()).severe("Failed to close JmDNS: " + e.getMessage());// Log closing errors
				}
			}
		}
	}
}
