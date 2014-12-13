package com.tomdevito.solutionService;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.tomdevito.solutionService.dto.MessageObject;
import com.tomdevito.solutionService.formula.FormulaSolver;

import java.io.IOException;

public class SolutionService {

    private FormulaSolver formulaSolver;
    private Gson gson;

    // get these values from the environment
    private static final String DEFAULT_RABBIT_MQ_HOSTNAME = "localhost";
    private static final String AMQ_TOPIC_EXCHANGE = "amq.topic";
    private static final String SOLVED_SOLUTION_TOPIC = "formula_solution";

    public SolutionService(FormulaSolver formulaSolver) {
        this.formulaSolver = formulaSolver;
        this.gson = new Gson();
    }

    public SolutionService() {
        this.formulaSolver = new FormulaSolver();
        this.gson = new Gson();
    }

    public void startService(String hostname) throws IOException, InterruptedException{
        if(hostname == null || hostname.equals("")) {
            hostname = DEFAULT_RABBIT_MQ_HOSTNAME;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        System.out.println(" *** Trying to connect to: " + hostname);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, AMQ_TOPIC_EXCHANGE, "");

        System.out.println(" *** Waiting for messages on queue [" + queueName + "].  To exit press ctrl-c");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" Received '" + message + "'");
            handleMessage(message);
        }
    }

    private void handleMessage(String message) throws IOException, NumberFormatException {
        MessageObject messageObject = gson.fromJson(message, MessageObject.class);

        try {
            messageObject.setSolution(formulaSolver.solveFormula(messageObject.getFormula()));
        } catch (Exception e) {
            System.out.println("Error getting solution: " +  e.getMessage());
            return;
        }

        sendSolution(messageObject);
    }

    private void sendSolution(MessageObject messageObject) throws IOException{
        String jsonToSend = gson.toJson(messageObject);

        // Should I be doing this for each send?
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(DEFAULT_RABBIT_MQ_HOSTNAME);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.basicPublish(AMQ_TOPIC_EXCHANGE, SOLVED_SOLUTION_TOPIC, null, jsonToSend.getBytes());
        System.out.println(" Sent '" + jsonToSend + "'");

        channel.close();
        connection.close();
    }
}
