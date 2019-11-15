package org.bridgelabz.fundoonotes.service;

import org.bridgelabz.fundoonotes.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	
	private String exchange="javainuse.exchange";
	

	private String routingkey; 
	
	public void send(User company) {
		rabbitTemplate.convertAndSend(exchange, routingkey, company.getEmail());
		System.out.println("Send msg = " + company);
	    
	}
}
