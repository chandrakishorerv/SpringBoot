package org.bridgelabz.fundoonotes.configuration;

import org.bridgelabz.fundoonotes.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

	@RabbitListener(queues = "queue108")
	public void recievedMessage(User user) {
		System.out.println("Recieved Message From RabbitMQ: " + user);
	}
}
