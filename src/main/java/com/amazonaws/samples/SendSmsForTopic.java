package com.amazonaws.samples;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.MessageAttributeValue;

public class SendSmsForTopic {
	
	private List<Paciente> listaPacientes;
	
	public SendSmsForTopic(List<Paciente> listaPacientes, String sms) {
		
		this.listaPacientes =  listaPacientes;
		
		/*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (C:\\Users\\user\\.aws\\credentials).11111111
         */
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Não foi encontrado as credenciais"
        			+ "em (C:\\Users\\user\\.aws\\credentials)");
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\user\\.aws\\credentials), and is in valid format.",
                    e);
        }
        
      //create a new SNS client and set endpoint
    	AmazonSNS snsClient =  AmazonSNSClientBuilder.standard()
    			.withCredentials(credentialsProvider)
    			.withRegion(Regions.US_WEST_2)
    			.build();
    	
    	System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SNS");
        System.out.println("===========================================\n");
        
        String topicArn = createSNSTopic(snsClient);
        
        for(Paciente paciente: listaPacientes) {
        	subscribeTopic(snsClient, topicArn, paciente.getTelefone());
        }
        

        publishTopic(snsClient, topicArn, sms);
    	deleteTopic(snsClient, topicArn);
		
	}
	

	
	public static String createSNSTopic(AmazonSNS snsClient) {
		//create a new SNS topic
		CreateTopicRequest createTopicRequest = new CreateTopicRequest("MyNewTopic");
		CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
		//print TopicArn
		System.out.println(createTopicResult);
		//get request id for CreateTopicRequest from SNS metadata		
		System.out.println("CreateTopicRequest - " + 
		snsClient.getCachedResponseMetadata(createTopicRequest));
		return createTopicResult.getTopicArn();
	}
	
	public static void subscribeTopic(AmazonSNS snsClient, String topicArn, String number) {
		
		//subscribe to an SNS topic
		SubscribeRequest subRequest = new SubscribeRequest(topicArn, "sms", "+55" + number);
		snsClient.subscribe(subRequest);
		//get request id for SubscribeRequest from SNS metadata
		System.out.println("SubscribeRequest - " + snsClient.getCachedResponseMetadata(subRequest));
		System.out.println("Check your phone and confirm subscription.");
	}
	
	public static void publishTopic(AmazonSNS snsClient, String topicArn, String sms) {
		
		Map<String, MessageAttributeValue> smsAttributes =
		        new HashMap<String, MessageAttributeValue>();
		smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
		        .withStringValue("0.50") //Sets the max price to 0.50 USD.
		        .withDataType("Number"));
		smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
		        .withStringValue("Promotional") //Sets the type to promotional.
		        .withDataType("String"));
		
		//publish to an SNS topic
		PublishRequest publishRequest = new PublishRequest()
				.withTopicArn(topicArn)
                .withMessage(sms)
                .withMessageAttributes(smsAttributes);
		PublishResult publishResult = snsClient.publish(publishRequest);
		//print MessageId of message published to SNS topic
		System.out.println("MessageId - " + publishResult.getMessageId());
		JOptionPane.showMessageDialog(null, "MessageId - " + publishResult.getMessageId());
	}
	
	public static void deleteTopic(AmazonSNS snsClient, String topicArn) {
		//delete an SNS topic
		DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
		snsClient.deleteTopic(deleteTopicRequest);
		//get request id for DeleteTopicRequest from SNS metadata
		System.out.println("DeleteTopicRequest - " + snsClient.getCachedResponseMetadata(deleteTopicRequest));
	}

}
