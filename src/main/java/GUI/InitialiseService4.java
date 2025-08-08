/*------------------------------------------------------------------------------------------------------------------
 * InitialiseService4 class
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
 * This class implements a gRPC server for handling Chat. 
 * It starts the server,  checks port availability and logs server status. 
 *  
------------------------------------------------------------------------------------------------------------------*/

package GUI;

// Import necessary libraries
import java.awt.Color;// Import for GUI components
import java.awt.event.ActionEvent;// Import for GUI action events
import java.awt.event.ActionListener; // Import for GUI action listeners

import java.util.logging.Logger;// Import for logging
import javax.swing.*;// Import for Swing components

import grpc.foodaid.Chat.ChatClient;// Import for Chat client
import grpc.foodaid.Chat.ChatRequest;//	 Import for Chat request
import grpc.foodaid.Chat.ChatResponse;// Import for Chat response
import grpc.foodaid.Chat.ChatServer;// Import for Chat server
import io.grpc.stub.StreamObserver;//	 Import for gRPC stream observer

public class InitialiseService4 {

	private boolean ignoreResponses = false;// Flag to ignore responses

	private StringBuilder currentText = new StringBuilder("<table width='100%'>");// StringBuilder to hold chat messages
//    // GUI components for Service 4
	public JTextField txtSenderName;
	public JTextField txtMessage;
	public JLabel lblSenderName;
	public JLabel lblMessage;
	public JLabel lblS4Response;
	public JLabel lblS4ERROR;
	public JPanel groupPanelService4;
	public JPanel groupPanelService4_btn;
	public JButton btnStartService4;
	public JButton btnSubmitS4;

	// public JTextField txtQuantity;
	public InitialiseService4(JFrame frame, int portS4) {

		txtSenderName = new JTextField();
		txtSenderName.setBounds(195, 63, 211, 20);
		txtSenderName.setColumns(10);

		txtMessage = new JTextField();
		txtMessage.setColumns(10);
		txtMessage.setBounds(195, 94, 211, 20);

		lblS4Response = new JLabel("");
		lblS4Response.setVerticalAlignment(SwingConstants.TOP);
		lblS4Response.setBounds(87, 150, 400, 100);

		lblS4ERROR = new JLabel("");
		lblS4ERROR.setForeground(Color.RED);
		lblS4ERROR.setBounds(87, 250, 400, 20);

		lblSenderName = new JLabel("Sender Name");
		lblSenderName.setBounds(87, 66, 120, 14);

		lblMessage = new JLabel("Message");
		lblMessage.setBounds(87, 97, 120, 14);

		btnSubmitS4 = new JButton("Send");
		btnSubmitS4.setBackground(Color.GREEN);
		btnSubmitS4.setBounds(317, 11, 89, 23);

		btnStartService4 = new JButton("Start Chat Service");
		btnStartService4.setBackground(Color.RED);
		btnStartService4.setBounds(178, 11, 179, 23);

		groupPanelService4 = new JPanel();
		groupPanelService4.setLayout(null);
		groupPanelService4.setBounds(0, 50, 591, 314);

		groupPanelService4.add(txtSenderName);
		groupPanelService4.add(txtMessage);
		groupPanelService4.add(lblS4Response);
		groupPanelService4.add(lblSenderName);
		groupPanelService4.add(lblMessage);
		groupPanelService4.add(btnSubmitS4);
		groupPanelService4_btn = new JPanel();
		groupPanelService4_btn.setLayout(null);
		groupPanelService4_btn.setBounds(0, 11, 591, 38);
		groupPanelService4_btn.add(btnStartService4);
		groupPanelService4.setVisible(false);
		frame.getContentPane().add(groupPanelService4);
		frame.getContentPane().add(groupPanelService4_btn);

		btnStartService4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService4.setText("Starting Chat Service...");// Update button text
				new Thread(() -> {
					try {
						ChatServer.main(new String[] { String.valueOf(portS4) });// Start the Chat server
						SwingUtilities.invokeLater(() -> btnStartService4.setText("Chat Service Started"));// Update
																											// button
																											// text
					} catch (Exception ex) {
						ex.printStackTrace();
						SwingUtilities
								.invokeLater(() -> lblS4ERROR.setText("Failed to start server: " + ex.getMessage()));// Display
																														// error
																														// message
					}
				}).start();
				btnStartService4.setEnabled(false);// Disable the button after starting the service
				btnStartService4.setBackground(Color.GRAY);
				groupPanelService4.setVisible(true);// Show the service panel
			}
		});

		ChatClient client = new ChatClient("localhost", portS4);// Create a Chat client instance
		StreamObserver<ChatResponse> responseObserver = new StreamObserver<ChatResponse>() {// Create a response
																							// observer for handling
																							// chat responses

			public void onNext(ChatResponse value) {
				String align = ignoreResponses ? "right" : "left";// Align text based on ignoreResponses flag
				String color = ignoreResponses ? "blue" : "gray";// Color text based on ignoreResponses flag
				currentText.append("<tr>").append("<td align='").append(align).append("' style='color:").append(color)
						.append("'><i>").append(value.getSenderName()).append("</i></td>").append("<td>")
						.append(value.getResponseMessage()).append("</td>").append("</tr>");
				lblS4Response.setText("<html>" + currentText.toString() + "</table></html>");
				ignoreResponses = false;

				Logger.getLogger(InitialiseService4.class.getName())
						.info("Received message from " + value.getSenderName() + ": " + value.getResponseMessage());// Log
																													// the
																													// received
																													// message

				System.out
						.println(currentText);// Print
																														// the
																														// received
																														// message
			}

			@Override
			public void onError(Throwable t) {
				lblS4ERROR.setText("Error: " + t.getMessage());// Display error message
				Logger.getLogger(InitialiseService4.class.getName()).severe("gRPC error: " + t.getMessage());// Log the
																												// error
																												// message
			}

			@Override
			public void onCompleted() {
				lblS4ERROR.setText("Stream completed.");// Display completion message

			}
		};

		StreamObserver<ChatRequest> requestObserver = client.chatStub.liveChat(responseObserver);// Create a request
																									// observer for
																									// sending chat
																									// requests

		btnSubmitS4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Validate input fields
				String sender = txtSenderName.getText();
				String message = txtMessage.getText();
				if (sender == null || sender.trim().isEmpty() || message == null || message.trim().isEmpty()) {// Check
																												// if
																												// fields
																												// are
																												// empty
					lblS4ERROR.setText("All fields must be filled in.");
					return;
				}
				try {
					ChatRequest chat = ChatRequest.newBuilder().setMessage(message).setSenderName(sender).build();
					requestObserver.onNext(chat);// cREATE and Send the Chat request
					ignoreResponses = true; // Set flag to ignore responses for the sender
					txtMessage.setText("");// Clear the message field
				} catch (Exception ex) {
					lblS4ERROR.setText("Error: " + ex.getMessage());
				}
			}
		});

	}
}