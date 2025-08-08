/*------------------------------------------------------------------------------------------------------------------
 * InitialiseService1 class
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
 * This class provides a GUI for interacting with the Surplus Food gRPC service. 
 * It allows users to input surplus food details and sends the data to the gRPC server. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package GUI;

//// Import libraries
import java.awt.Color; // Import Color for button background colors
import java.awt.event.ActionEvent;// Import ActionEvent for button actions
import java.awt.event.ActionListener;// Import ActionListener for button actions
import javax.swing.*; // Import Swing components for GUI

import grpc.foodaid.Surplus.SurplusAcknowledge;// Import SurplusAcknowledge for server responses
import grpc.foodaid.Surplus.SurplusClient;// Import SurplusClient for gRPC client functionality
import grpc.foodaid.Surplus.SurplusRequest;// Import SurplusRequest for sending surplus food data
import grpc.foodaid.Surplus.SurplusServer;// Import SurplusServer for starting the gRPC server

public class InitialiseService1 {
	// GUI components for Surplus Service 1
	public JTextField txtFoodType;
	public JTextField txtQuantity;
	public JTextField txtLocation;
	public JTextField txtGrade;
	public JTextField txtDepo;
	public JLabel lblFoodType;
	public JLabel lblQuantity;
	public JLabel lblNutrisionGrad;
	public JLabel lblLocation;
	public JLabel lblDepo;
	public JLabel lblS1Response;
	public JPanel groupPanelService1;
	public JPanel groupPanelService1_btn;
	public JButton btnStartService1;
	public JButton btnSubmit;

	// Constructor for InitialiseService1
	public InitialiseService1(JFrame frame, int portS1) {
		// Set the frame properties
		txtFoodType = new JTextField();
		txtFoodType.setBounds(195, 63, 211, 20);
		txtFoodType.setColumns(10);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(195, 94, 211, 20);

		txtLocation = new JTextField();
		txtLocation.setColumns(10);
		txtLocation.setBounds(195, 156, 211, 20);

		txtGrade = new JTextField();
		txtGrade.setColumns(10);
		txtGrade.setBounds(195, 125, 211, 20);

		txtDepo = new JTextField();
		txtDepo.setColumns(10);
		txtDepo.setBounds(195, 187, 211, 20);

		lblS1Response = new JLabel("");
		lblS1Response.setForeground(Color.BLUE);
		lblS1Response.setBounds(39, 218, 514, 77);

		lblFoodType = new JLabel("Food Type");
		lblFoodType.setBounds(87, 66, 120, 14);

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(87, 97, 120, 14);

		lblNutrisionGrad = new JLabel("Nutrition grade");
		lblNutrisionGrad.setBounds(87, 128, 120, 14);

		lblLocation = new JLabel("Location");
		lblLocation.setBounds(87, 159, 120, 14);

		lblDepo = new JLabel("Depo");
		lblDepo.setBounds(87, 193, 120, 14);

		btnSubmit = new JButton("Send");
		btnSubmit.setBackground(Color.GREEN);
		btnSubmit.setBounds(317, 11, 89, 23);

		btnStartService1 = new JButton("Start Surplus Service");
		btnStartService1.setBackground(Color.RED);
		btnStartService1.setBounds(178, 11, 179, 23);

		groupPanelService1 = new JPanel();
		groupPanelService1.setLayout(null);
		groupPanelService1.setBounds(0, 50, 591, 314);

		// Add components to the group panel for Surplus Service 1
		groupPanelService1.add(txtFoodType);
		groupPanelService1.add(txtQuantity);
		groupPanelService1.add(txtLocation);
		groupPanelService1.add(txtGrade);
		groupPanelService1.add(txtDepo);
		groupPanelService1.add(lblS1Response);
		groupPanelService1.add(lblFoodType);
		groupPanelService1.add(lblQuantity);
		groupPanelService1.add(lblNutrisionGrad);
		groupPanelService1.add(lblLocation);
		groupPanelService1.add(lblDepo);
		groupPanelService1.add(btnSubmit);

		// Create a button panel for Surplus Service 1
		groupPanelService1_btn = new JPanel();
		groupPanelService1_btn.setLayout(null);
		groupPanelService1_btn.setBounds(0, 11, 591, 38);
		groupPanelService1_btn.add(btnStartService1);

		groupPanelService1.setVisible(false);

		btnStartService1.addActionListener(new ActionListener() { // Action listener for the start service button
			public void actionPerformed(ActionEvent e) {
				btnStartService1.setText("Starting Surplus Service...");// Change button text to indicate service start
				new Thread(() -> {
					SurplusServer.main(new String[] { String.valueOf(portS1) });// Start the Surplus gRPC server with
																				// the specified port
					SwingUtilities.invokeLater(() -> btnStartService1.setText("Surplus Service Started"));// Update
																											// button
																											// text on
																											// the Event
																											// Dispatch
																											// Thread

				}).start(); // Start the service
				btnStartService1.setEnabled(false);// Disable the button to prevent multiple clicks

				btnStartService1.setBackground(Color.GRAY);// Change button color to indicate service is running
				groupPanelService1.setVisible(true); // Make the group panel visible
			}
		});

		frame.getContentPane().add(groupPanelService1); // Add the group panel to the frame
		frame.getContentPane().add(groupPanelService1_btn); // Add the button panel to the frame

		btnSubmit.addActionListener(new ActionListener() {// Action listener for the submit button
			@Override
			public void actionPerformed(ActionEvent e) {// Handle the submit button click event
				// Retrieve input values from text fields
				String foodType = txtFoodType.getText();
				String quantityStr = txtQuantity.getText();
				String gradeStr = txtGrade.getText();
				String location = txtLocation.getText();
				String depoStr = txtDepo.getText();

				// Input validation
				if (foodType.isEmpty() || quantityStr.isEmpty() || gradeStr.isEmpty() || location.isEmpty()
						|| depoStr.isEmpty()) {// Check if any field is empty
					lblS1Response.setText("Error: All fields must be filled.");// Display error message if any field is
																				// empty
					return;
				}

				int quantity, grade, depo;
				try {
					// Parse input values to integers
					quantity = Integer.parseInt(quantityStr);
					grade = Integer.parseInt(gradeStr);
					depo = Integer.parseInt(depoStr);
				} catch (NumberFormatException nfe) {
					lblS1Response.setText("Error: Quantity, Grade, and Depo must be valid numbers.");// Display error
																										// message if
																										// parsing fails
					return;
				}

				SurplusRequest request = SurplusRequest.newBuilder()// Build a SurplusRequest with the input values
						.setFoodType(foodType).setQuantityFood(quantity).setNutritionalGrade(grade)
						.setLocation(location).setDepo(depo).build();

				SurplusClient client = new SurplusClient("localhost", portS1);// Create a new SurplusClient instance
																				// with the server address and port
				try {
					SurplusAcknowledge response = client.recordSurplus(request);// Send the request to the server and
																				// receive the response
					lblS1Response.setText("Server response: " + response.getMessage());// Display the server response in
																						// the label
				} catch (io.grpc.StatusRuntimeException ex) {// Handle gRPC status runtime exceptions
					lblS1Response.setText("gRPC error: " + ex.getStatus().getDescription());
				} catch (Exception ex) {// Handle general exceptions
					lblS1Response.setText("Unexpected error: " + ex.getMessage());// Display unexpected error messages
				} finally {// Ensure the client is shut down properly
					try {
						client.shutdown();// Shutdown the client channel
					} catch (InterruptedException ignored) {// Ignore interruptions during shutdown
						Thread.currentThread().interrupt();// Restore the interrupted status
					}
				}
			}
		});
	}
}