package com.mdm.springcloudsqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.mdm.models.SignupEvent;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageReceiver {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

	@SqsListener(value="testObjectQueue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void reciveObjectMessage(final SignupEvent message, 
			@Header("SenderId") String senderId ) {
		
		logger.info("Message was recieved ", senderId, message);;
	}
}
