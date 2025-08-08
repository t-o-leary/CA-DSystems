// src/main/java/GUI/initialiseService1.java
package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.swing.*;

import grpc.foodaid.Chat.ChatClient;
import grpc.foodaid.Chat.ChatRequest;
import grpc.foodaid.Chat.ChatResponse;
import grpc.foodaid.Chat.ChatServer;
import io.grpc.stub.StreamObserver;



public class InitialiseService4 {
	
	private boolean ignoreResponses = false;
	//private String currentText = "";
	
	private StringBuilder currentText = new StringBuilder("<table width='100%'>");
	
	public JTextField txtSenderName;
	public JTextField txtMessage;
	//public JTextField txtmessage;
	//public JTextField txtDeliveryAddress;
	//public JTextField txtOrderId;
	public JLabel lblSenderName;
	public JLabel lblMessage ;
	//public JLabel lblRecipient;
	//public JLabel lblDeliveryAddress;
	//public JLabel lblOrderId;
	public JLabel lblS4Response;
	public JLabel lblS4ERROR ;
	//public JLabel lblS3Response2;
	//public JLabel lblS3ResponseSub;
	public JPanel groupPanelService4;
	public JPanel groupPanelService4_btn;
	public JButton btnStartService4;
	public JButton btnSubmitS4;
	//public JButton btnCompleteS3;

	public InitialiseService4(JFrame frame, int portS4) {
		
		
		
		
		txtSenderName = new JTextField();
		txtSenderName.setBounds(195, 63, 211, 20);
		txtSenderName.setColumns(10);

		txtMessage = new JTextField();
		txtMessage.setColumns(10);
		txtMessage.setBounds(195, 94, 211, 20);

		//txtQuantity = new JTextField();
		//txtQuantity.setColumns(10);
		//txtQuantity.setBounds(195, 125, 211, 20);

		//txtDeliveryAddress = new JTextField();
		//txtDeliveryAddress.setColumns(10);
		//txtDeliveryAddress.setBounds(195, 156, 211, 20);

		lblS4Response = new JLabel("");
		lblS4Response.setVerticalAlignment(SwingConstants.TOP);
		lblS4Response.setBounds(87, 150, 400, 100); // Increase height
		
		lblS4ERROR = new JLabel("");
		lblS4ERROR.setForeground(Color.RED);
		lblS4ERROR.setBounds(87, 250, 400, 20); // Position below the response label
		
		//lblS3Response2 = new JLabel("XXXXXXXXXXXXXXXXXXXXXX");
		//lblS3Response2.setBounds(39, 235, 514, 77);
		//lblS3Response2.setForeground(Color.BLUE);
			
		lblSenderName = new JLabel("Sender Name");
		lblSenderName.setBounds(87, 66, 120, 14);

		lblMessage  = new JLabel("Message");
		lblMessage.setBounds(87, 97, 120, 14);

		//lblQuantity = new JLabel("Quantity");
		//lblQuantity.setBounds(87, 128, 120, 14);

		//lblDeliveryAddress = new JLabel("Delivery Address");
		//lblDeliveryAddress.setBounds(87, 159, 120, 14);

		btnSubmitS4 = new JButton("Send");
		btnSubmitS4.setBackground(Color.GREEN);
		btnSubmitS4.setBounds(317, 11, 89, 23);

	//	txtOrderId = new JTextField();
	//	txtOrderId.setColumns(10);
	//	txtOrderId.setBounds(195, 217, 211, 20);

	//	lblOrderId = new JLabel("Order ID");
	//	lblOrderId.setBounds(87, 220, 120, 14);

		//btnCompleteS3 = new JButton("Complete");

		//btnCompleteS3.setBackground(Color.ORANGE);
		//btnCompleteS3.setBounds(300, 187, 89, 23);

	//	lblS3ResponseSub = new JLabel("");
	//	lblS3ResponseSub.setForeground(Color.BLUE);
	//	lblS3ResponseSub.setBounds(39, 235, 514, 77);

		btnStartService4 = new JButton("Start Chat Service");
		btnStartService4.setBackground(Color.RED);
		btnStartService4.setBounds(178, 11, 179, 23);

		groupPanelService4 = new JPanel();
		groupPanelService4.setLayout(null);
		groupPanelService4.setBounds(0, 50, 591, 314);

		groupPanelService4.add(txtSenderName);
		//groupPanelService4.add(txtQuantity);
		groupPanelService4.add(txtMessage);
		//groupPanelService3.add(txtDeliveryAddress);

		groupPanelService4.add(lblS4Response);
		//groupPanelService3.add(lblS3Response2);
		groupPanelService4.add(lblSenderName);
		groupPanelService4.add(lblMessage);
		//groupPanelService3.add(lblRecipient);
		//groupPanelService3.add(lblDeliveryAddress);

		groupPanelService4.add(btnSubmitS4);
		//groupPanelService3.add(txtOrderId);
		//groupPanelService3.add(lblOrderId);
		//groupPanelService3.add(btnCompleteS3);
		//groupPanelService3.add(lblS3ResponseSub);

		groupPanelService4_btn = new JPanel();
		groupPanelService4_btn.setLayout(null);
		groupPanelService4_btn.setBounds(0, 11, 591, 38);
		groupPanelService4_btn.add(btnStartService4);

		groupPanelService4.setVisible(false);
		frame.getContentPane().add(groupPanelService4);
		frame.getContentPane().add(groupPanelService4_btn);

		btnStartService4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartService4.setText("Starting Chat Service...");
				new Thread(() -> {
					SwingUtilities.invokeLater(() -> btnStartService4.setText("Chat Service Started"));
					
					try {
						ChatServer.main(new String[] { String.valueOf(portS4) });
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}).start();
				btnStartService4.setEnabled(false);
				btnStartService4.setBackground(Color.GRAY);
				groupPanelService4.setVisible(true);
			}
		});

		
		
		 ChatClient client = new ChatClient("localhost", portS4);
        StreamObserver<ChatResponse> responseObserver = new StreamObserver<ChatResponse>() {
        	
        	
        	
        	public void onNext(ChatResponse value) {
        	    String align = ignoreResponses ? "right" : "left";// Align text based on ignoreResponses flag
        	    String color = ignoreResponses ? "blue" : "gray";// Color text based on ignoreResponses flag
        	    currentText.append("<tr>")
        	        .append("<td align='").append(align).append("' style='color:").append(color).append("'><i>")
        	        .append(value.getSenderName()).append("</i></td>")
        	        .append("<td>").append(value.getResponseMessage()).append("</td>")
        	        .append("</tr>");
        	    lblS4Response.setText("<html>" + currentText.toString() + "</table></html>");
        	    ignoreResponses = false;
        	
        	    
        	    
        	    Logger.getLogger(InitialiseService4.class.getName()).info("Received message from " + value.getSenderName() + ": " + value.getResponseMessage());
        	   
        	    Logger.getLogger(InitialiseService4.class.getName()).info("==================================================: ");
        	    
        	    Logger.getLogger(InitialiseService4.class.getName()).info(lblS4Response.getText());
        	    
        	    
        	   
        	}

            @Override
            public void onError(Throwable t) {
            	lblS4ERROR.setText("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
            	lblS4ERROR.setText("Stream completed.");
                
            }
        };

        StreamObserver<ChatRequest> requestObserver = client.chatStub.liveChat(responseObserver);
		
		btnSubmitS4.addActionListener(new ActionListener() {
			
			
			@Override
            public void actionPerformed(ActionEvent e) {
                
		    	
				 
			        try {
			            
			            ChatRequest chat = ChatRequest.newBuilder()
			                .setMessage(txtMessage.getText())
			                .setSenderName(txtSenderName.getText())
			                .build();
			            requestObserver.onNext(chat);
			           // lblS4Response.setText("Submitting delivery order for " + txtMessage.getText());	
			            ignoreResponses = true; // Set to true to ignore responses
			            txtMessage.setText("");
			            //txtSenderName.setText("");
			            
			        } catch (NumberFormatException ex) {
			            lblS4ERROR.setText("Quantity must be a valid number.");
			        } catch (Exception ex) {
			            lblS4ERROR.setText("Error: " + ex.getMessage());
			        }
			    }
			});
		
		
	
		
		
	}
}