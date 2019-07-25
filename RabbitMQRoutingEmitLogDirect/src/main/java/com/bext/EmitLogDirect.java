package com.bext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {
	private static final String EXCHANGE_NAME = "direct_logs";
			
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			
			String logLevel = getLogLevel(args);
			String message = getMessage(args);
			
			AMQP.BasicProperties props = null;
			channel.basicPublish(EXCHANGE_NAME, logLevel, props, message.getBytes("UTF-8"));
			System.out.println(" [x] Enviado '" + logLevel + "':'" + message + "'");
		} 
	}

	private static String getMessage(String[] args) {
		return args[1];
	}

	private static String getLogLevel(String[] args) {
		return args[0];
	}

}
