/*------------------------------------------------------------------------------------------------------------------
 * InitialiseService3 class
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
 * The InitialiseService3 class provides a GUI for interacting with the Delivery gRPC service.
 * It enables users to submit delivery orders and complete delivery streams.
 *  
------------------------------------------------------------------------------------------------------------------*/
package GUI;

// Import libraries and packages
import java.awt.Color;// Import necessary libraries for GUI components
import java.awt.event.ActionEvent;// Import necessary libraries for handling events
import java.awt.event.ActionListener;// Import necessary libraries for handling events
import java.io.IOException;

import javax.swing.*;// Import necessary libraries for GUI components

import grpc.foodaid.Deliver.DeliveryClient;// Import the DeliveryClient class for gRPC communication
import grpc.foodaid.Deliver.DeliveryOrder;// Import the DeliveryOrder class for creating delivery orders
import grpc.foodaid.Deliver.DeliveryPlan;// Import the DeliveryPlan class for handling delivery plans
import grpc.foodaid.Deliver.DeliveryServer;// Import the DeliveryServer class for starting the delivery service
import io.grpc.StatusRuntimeException;// Import the StatusRuntimeException class for handling gRPC errors
import io.grpc.stub.StreamObserver;// Import the StreamObserver class for handling asynchronous responses

public class InitialiseService3 {
	// GUI components for the Delivery service
	public JTextField txtFoodType;
	public JTextField txtQuantity;
	public JTextField txtRecipient;
	public JTextField txtDeliveryAddress;
	public JLabel lblFoodType;
	public JLabel lblQuantity;
	public JLabel lblRecipient;
	public JLabel lblDeliveryAddress;
	public JLabel lblS3Response;
	public JPanel groupPanelService3;
	public JPanel groupPanelService3_btn;
	public JButton btnStartService3;
	public JButton btnSubmitS3;
	public JButton btnCompleteS3;

// This class initialises the GUI components for the Delivery service
	public InitialiseService3(JFrame frame, int portS3) {
		txtRecipient = new JTextField();
		txtRecipient.setBounds(195, 63, 211, 20);
		txtRecipient.setColumns(10);

		txtFoodType = new JTextField();
		txtFoodType.setColumns(10);
		txtFoodType.setBounds(195, 94, 211, 20);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(195, 125, 211, 20);

		txtDeliveryAddress = new JTextField();
		txtDeliveryAddress.setColumns(10);
		txtDeliveryAddress.setBounds(195, 156, 211, 20);

		lblS3Response = new JLabel("");
		lblS3Response.setBounds(55, 12, 514, 77);
		lblS3Response.setFont(lblS3Response.getFont().deriveFont(16f));
		lblS3Response.setForeground(Color.BLUE);

		lblRecipient = new JLabel("Recipient");
		lblRecipient.setBounds(87, 66, 120, 14);

		lblFoodType = new JLabel("Food Type");
		lblFoodType.setBounds(87, 97, 120, 14);

		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(87, 128, 120, 14);

		lblDeliveryAddress = new JLabel("Delivery Address");
		lblDeliveryAddress.setBounds(87, 159, 120, 14);

		btnSubmitS3 = new JButton("Send");
		btnSubmitS3.setBackground(Color.GREEN);
		btnSubmitS3.setBounds(317, 11, 89, 23);

		btnCompleteS3 = new JButton("Complete");

		btnCompleteS3.setBackground(Color.ORANGE);
		btnCompleteS3.setBounds(300, 187, 89, 23);

		btnStartService3 = new JButton("Start Delivery Service");
		btnStartService3.setBackground(Color.RED);
		btnStartService3.setBounds(178, 11, 179, 23);

		groupPanelService3 = new JPanel();
		groupPanelService3.setLayout(null);
		groupPanelService3.setBounds(0, 50, 591, 314);

		groupPanelService3.add(txtFoodType);
		groupPanelService3.add(txtQuantity);
		groupPanelService3.add(txtRecipient);
		groupPanelService3.add(txtDeliveryAddress);

		groupPanelService3.add(lblS3Response);
		groupPanelService3.add(lblFoodType);
		groupPanelService3.add(lblQuantity);
		groupPanelService3.add(lblRecipient);
		groupPanelService3.add(lblDeliveryAddress);

		groupPanelService3.add(btnSubmitS3);
		groupPanelService3.add(btnCompleteS3);

		groupPanelService3_btn = new JPanel();
		groupPanelService3_btn.setLayout(null);
		groupPanelService3_btn.setBounds(0, 11, 591, 38);
		groupPanelService3_btn.add(btnStartService3);

		groupPanelService3.setVisible(false);
		frame.getContentPane().add(groupPanelService3);
		frame.getContentPane().add(groupPanelService3_btn);

		btnStartService3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// Action listener for the Start Delivery Service button
				btnStartService3.setText("Starting Delivery Service...");// Change button text to indicate service is
																			// starting
				new Thread(() -> {
		            try {
		                DeliveryServer.main(new String[] { String.valueOf(portS3) });
		                SwingUtilities.invokeLater(() -> btnStartService3.setText("Delivery Service Started"));
		            }  catch (Exception ex) {
		                ex.printStackTrace();//	 Handle any other exceptions that may occur
		                SwingUtilities.invokeLater(() -> lblS3Response.setText("Unexpected error: " + ex.getMessage()));
		            }																	// specified port
				}).start();// Start a new thread to avoid blocking the GUI
				btnStartService3.setEnabled(false);// Disable the button to prevent multiple clicks
				btnStartService3.setBackground(Color.GRAY);// Change button color to indicate service is running
				groupPanelService3.setVisible(true);// Show the delivery service panel
			}
		});

		DeliveryClient client = new DeliveryClient("localhost", portS3);// Create a new DeliveryClient instance to
																		// connect to the Delivery service
		StreamObserver<DeliveryPlan> responseObserver = new StreamObserver<DeliveryPlan>() {// Create a StreamObserver
																							// to handle responses from
																							// the Delivery service
			@Override
			public void onNext(DeliveryPlan value) {// Handle the next DeliveryPlan response
				lblS3Response.setText("Delivery order submitted successfully.");// Update the response label with
																				// success message

			}

			@Override
			public void onError(Throwable t) {// Handle errors from the Delivery service
				lblS3Response.setText("Error: " + t.getMessage());// Update the response label with error message
			}

			@Override
			public void onCompleted() {
				lblS3Response.setText("Stream completed.");// Update the response label when the stream is completed
			}
		};

		StreamObserver<DeliveryOrder> requestObserver = client.asyncStub.submitDeliveries(responseObserver);// Create a
																											// StreamObserver
																											// to send
																											// DeliveryOrder
																											// requests
																											// to the
																											// Delivery
																											// service

		btnSubmitS3.addActionListener(new ActionListener() {// Action listener for the Submit button
			@Override
			public void actionPerformed(ActionEvent e) {// Handle the Submit button click event
				String recipient = txtRecipient.getText();// Get the recipient from the input field
				String foodType = txtFoodType.getText();
				String quantityText = txtQuantity.getText();
				String deliveryAddress = txtDeliveryAddress.getText();
// Check if any of the fields are empty
				if (recipient == null || recipient.trim().isEmpty() || foodType == null || foodType.trim().isEmpty()
						|| quantityText == null || quantityText.trim().isEmpty() || deliveryAddress == null
						|| deliveryAddress.trim().isEmpty()) {
					lblS3Response.setText("All fields must be filled in.");
					return;
				}

				if (quantityText == null || quantityText.trim().isEmpty()) {
					lblS3Response.setText("Quantity cannot be empty.");// Update the response label if quantity is empty
					return;
				}
				try {
					int quantity = Integer.parseInt(quantityText.trim());// Parse the quantity text to an integer
					DeliveryOrder order = DeliveryOrder.newBuilder().setRecipient(txtRecipient.getText())
							.setDeliveryAddress(txtDeliveryAddress.getText()).setFoodType(txtFoodType.getText())
							.setQuantity(quantity).build();
					try {
						requestObserver.onNext(order);// Send the DeliveryOrder to the Delivery service
						lblS3Response.setText("Submitting delivery order for " + txtRecipient.getText());// Update the
																											// response
																											// label
																											// with
																											// submission
																											// message
						// Clear the input fields after submission
						txtRecipient.setText("");
						txtFoodType.setText("");
						txtQuantity.setText("");
						txtDeliveryAddress.setText("");
					} catch (StatusRuntimeException ex) {
						lblS3Response.setText("gRPC error: " + ex.getStatus().getDescription());// Update the response
																								// label with gRPC error
																								// message
					} catch (Exception ex) {
						lblS3Response.setText("Unexpected error: " + ex.getMessage());// Update the response label with
																						// unexpected error message
					}
				} catch (NumberFormatException ex) {
					lblS3Response.setText("Quantity must be a valid number.");// quantity is not a valid number
				}
			}
		});

		btnCompleteS3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					requestObserver.onCompleted();// Complete the stream of DeliveryOrder requests
					lblS3Response.setText("Delivery stream completed.");// Update the response label to indicate
																		// completion
				} catch (StatusRuntimeException ex) {
					lblS3Response.setText("gRPC error: " + ex.getStatus().getDescription());// Update the response label
																							// with gRPC error message
				} catch (Exception ex) {
					lblS3Response.setText("Unexpected error: " + ex.getMessage());// Update the response label with
																					// unexpected error message
				}
			}
		});

	}
}
