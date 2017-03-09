package com.rom.util.mq;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Client implements MessageListener {
	private static int ackMode;
	private static String clientQueueName;
	private boolean transacted = false;
	private MessageProducer producer;
	static {
		clientQueueName = "client.messages";
		ackMode = Session.AUTO_ACKNOWLEDGE;
	}

	public Client() throws JMSException {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection;
	 		connection = connectionFactory.createConnection();
	 		connection.start();
	 		Session session = connection.createSession(transacted, ackMode);
	 		Destination adminQueue = session.createQueue(clientQueueName);
	 		
	 		this.producer = session.createProducer(adminQueue);
	 		this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	 		Destination tempDest = session.createTemporaryQueue();
	 		MessageConsumer responseConsumer = session.createConsumer(tempDest);
	 		responseConsumer.setMessageListener(this);
 
			TextMessage txtMessage = session.createTextMessage();
			txtMessage.setText("MyProtocolMessage");
			txtMessage.setJMSReplyTo(tempDest);
 
			String correlationId = this.createRandomString();
			txtMessage.setJMSCorrelationID(correlationId);
			this.producer.send(txtMessage);
	}

	private String createRandomString() {
			Random random = new Random(System.currentTimeMillis());
			long randomLong = random.nextLong();
			return Long.toHexString(randomLong);
	}

	public void onMessage(Message message) {
			String messageText = null;
		 	if (message instanceof TextMessage) {
		 		TextMessage textMessage = (TextMessage) message;
		 		try {
					messageText = textMessage.getText();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		System.out.println("messageText = " + messageText);
		 	}
	}

	public static void main(String[] args) throws JMSException {
		new Client();
	}
}