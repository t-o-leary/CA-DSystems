/*------------------------------------------------------------------------------------------------------------------
 * ConsoleInterface class
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
 * This class provides the main GUI for the Food Aid application, 
 * allowing users to access and interact with multiple gRPC services (Surplus, Order, Deliver, Chat) via menu 
 * options. It manages the initialization and display of service panels, checks port availability for 
 * each service, and controls the visibility of UI components based on user actions. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package GUI;

// Import libraries
import java.awt.EventQueue; // For managing the event dispatch thread
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;// For creating the main application window
import javax.swing.JMenuBar;// For creating a menu bar in the application
import javax.swing.JMenu;// For creating a menu within the menu bar
import javax.swing.JMenuItem;//	 For creating items within the menu

import java.io.IOException;// For handling IO exceptions
import java.net.ServerSocket;// For checking if a port is available

public class ConsoleInterface {
// Declare the main frame for the GUI
	public JFrame frame;
	public JMenuBar menuBar;
	public JMenu Services;
	public JMenuItem service1, service2, service3, service4;

	// Declare panels for each service
	private InitialiseService1 service1Panel;
	private InitialiseService2 service2Panel;
	private InitialiseService3 service3Panel;
	private InitialiseService4 service4Panel;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {// Run the GUI in the Event Dispatch Thread
			try {
				ConsoleInterface window = new ConsoleInterface();// Create an instance of ConsoleInterface
				window.frame.setVisible(true);// Make the main frame visible
			} catch (Exception e) {
				e.printStackTrace();// Print the stack trace if an exception occurs
			}
		});
	}

	public ConsoleInterface() { // Constructor for ConsoleInterface

		int portS1 = 50051; // Port for Surplus Service
		int portS2 = 50052; // Port for Order Service
		int portS3 = 50053; // Port for Deliver Service
		int portS4 = 50054; // Port for Chat Service

		initialiseFrame(); // Initialize the main frame and menu bar
// Create panels for each service
		service1Panel = new InitialiseService1(frame, portS1);
		service2Panel = new InitialiseService2(frame, portS2);
		service3Panel = new InitialiseService3(frame, portS3);
		service4Panel = new InitialiseService4(frame, portS4);

		service1.addActionListener(e -> {
			// Hide Service 1 panel when the menu item is clicked

			service1Panel.groupPanelService1_btn.setVisible(true);
// Hide Service 2 panel when the menu item is clicked
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			// Hide Service 3 panel when the menu item is clicked
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);
			// Hide Service 4 panel when the menu item is clicked
			service4Panel.groupPanelService4.setVisible(false);
			service4Panel.groupPanelService4_btn.setVisible(false);
			if (isPortAvailable(portS1)) {// Check if the port for Surplus Service is available

				service1Panel.groupPanelService1.setVisible(false);// If available, hide the service panel

			} else {
				service1Panel.groupPanelService1.setVisible(true);// If not available, show the service panel

			}

		});
		service2.addActionListener(e -> {
// Hide Service 2 panel when the menu item is clicked
			service2Panel.groupPanelService2_btn.setVisible(true);
			// Hide Service 1 panel when the menu item is clicked
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			// Hide Service 3 panel when the menu item is clicked
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);
			// Hide Service 4 panel when the menu item is clicked
			service4Panel.groupPanelService4.setVisible(false);
			service4Panel.groupPanelService4_btn.setVisible(false);

			if (isPortAvailable(portS2)) {

				service2Panel.groupPanelService2.setVisible(false);// If available, hide the service panel

			} else {
				service2Panel.groupPanelService2.setVisible(true);// If not available, show the service panel

			}
		});

		service3.addActionListener(e -> {
			// Hide Service 1 panel when the menu item is clicked

			service3Panel.groupPanelService3_btn.setVisible(true);
// Hide Service 2 panel when the menu item is clicked
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			// Hide Service 3 panel when the menu item is clicked
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			// Hide Service 4 panel when the menu item is clicked
			service4Panel.groupPanelService4.setVisible(false);
			service4Panel.groupPanelService4_btn.setVisible(false);

			if (isPortAvailable(portS4)) {

				service3Panel.groupPanelService3.setVisible(false);// If available, hide the service panel

			} else {
				service3Panel.groupPanelService3.setVisible(true);// If not available, show the service panel

			}
		});

		service4.addActionListener(e -> {
			// Show Service 4 panel when the menu item is clicked

			service4Panel.groupPanelService4_btn.setVisible(true);
			// Hide Service 1 panel when the menu item is clicked
			service1Panel.groupPanelService1.setVisible(false);
			service1Panel.groupPanelService1_btn.setVisible(false);
			// Hide Service 2 panel when the menu item is clicked
			service2Panel.groupPanelService2.setVisible(false);
			service2Panel.groupPanelService2_btn.setVisible(false);
			// Hide Service 3 panel when the menu item is clicked
			service3Panel.groupPanelService3.setVisible(false);
			service3Panel.groupPanelService3_btn.setVisible(false);

			if (isPortAvailable(portS4)) {

				service4Panel.groupPanelService4.setVisible(false);// If available, hide the service panel

			} else {
				service4Panel.groupPanelService4.setVisible(true);// If not available, show the service panel

			}
		});

	}

	public void initialiseFrame() {

		frame = new JFrame("Food Aid Console Interface"); // Create the main application window
		frame.setBounds(100, 100, 607, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Initialize the menu bar and add service menu items
		menuBar = new JMenuBar();

		// Create icons for the menu and service items
		ImageIcon oMenuIcon = new ImageIcon(getClass().getResource("/images/Menu.png"));
		Image scaledMenuImage = oMenuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon menuIcon = new ImageIcon(scaledMenuImage);

		ImageIcon oSurplusIcon = new ImageIcon(getClass().getResource("/images/Surplus.png"));
		Image scaledSurplusImage = oSurplusIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon surplusIcon = new ImageIcon(scaledSurplusImage);

		ImageIcon oOrderIcon = new ImageIcon(getClass().getResource("/images/Order.png"));
		Image scaledOrderImage = oOrderIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon orderIcon = new ImageIcon(scaledOrderImage);

		ImageIcon oDeliveryIcon = new ImageIcon(getClass().getResource("/images/Delivery.png"));
		Image scaledDeliveryImage = oDeliveryIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon deliveryIcon = new ImageIcon(scaledDeliveryImage);

		ImageIcon oChatIcon = new ImageIcon(getClass().getResource("/images/Chat.png"));
		Image scaledChatImage = oChatIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon chatIcon = new ImageIcon(scaledChatImage);

		// Create the Services menu and add service items with icons
		Services = new JMenu("Services Menu");
		Services.setIcon(menuIcon); // Set the icon for the menu

		service1 = new JMenuItem("Surplus Service");
		service1.setIcon(surplusIcon); // Set the icon for Surplus Service

		service2 = new JMenuItem("Order Service");
		service2.setIcon(orderIcon); // Set the icon for Order Service

		service3 = new JMenuItem("Deliver Service");
		service3.setIcon(deliveryIcon); // Set the icon for Deliver Service

		service4 = new JMenuItem("Chat Service");
		service4.setIcon(chatIcon); // Set the icon for Chat Service

// Add service items to the Services menu
		Services.add(service1);
		Services.add(service2);
		Services.add(service3);
		Services.add(service4);

		// Add the Services menu to the menu bar
		menuBar.add(Services);
		frame.setJMenuBar(menuBar);// Set the menu bar for the frame

	}

	public static boolean isPortAvailable(int iPort) {
		try (ServerSocket serverSocket = new ServerSocket(iPort)) {// Attempt to create a ServerSocket on the specified
																	// port
			serverSocket.setReuseAddress(true);// Allow the port to be reused
			return true;
		} catch (IOException e) {// If an IOException occurs, the port is not available
			return false;// Return false indicating the port is not available
		}
	}

}