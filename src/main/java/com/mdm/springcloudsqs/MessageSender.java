package com.mdm.springcloudsqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQSAsync;

import io.awspring.cloud.messaging.core.QueueMessageChannel;

//@Service
public class MessageSender {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
	
	private static final String QUEUE_NAME = "https://sqs.us-east-1.amazonaws.com/474715013863/testQueue";

	@Autowired
	private final AmazonSQSAsync amazonSQSAsync;
	
//	class constructor, used to initialize amazonSqs attribute
	@Autowired 
	public MessageSender(final AmazonSQSAsync amazonSqsAsync) {
		this.amazonSQSAsync = amazonSqsAsync;
	}
	
	public boolean send(final String messagePayload) {
//		use QueueMessageChanel to create the link between the app and AWS SQS URL
		MessageChannel messageChanel = 
				new QueueMessageChannel( amazonSQSAsync, QUEUE_NAME );
		
//		MessageBuilder class help us to create message
		Message<String> msg = MessageBuilder.withPayload( messagePayload )
				.setHeader("sender","app1")
				.setHeaderIfAbsent("contry", "MX")
				.build();
		
//		execute the send method
		long timeoutMillis = 5000;
		boolean sendStatus = messageChanel.send(msg, timeoutMillis);
		
//		print the message on the terminal
		logger.info("message wass send it...");
		
		return sendStatus;		
	}
}
