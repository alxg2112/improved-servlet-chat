package jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

/**
 * Producer class that adds messages to the ActiveMQ queue.
 */
public class ActiveMQProducer {
    private static final String QUEUE_NAME = "MessageQueue";

    private static final ActiveMQProducer instance = new ActiveMQProducer();
    private static ConnectionFactory connectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination destination;
    private static MessageProducer producer;

    private ActiveMQProducer() {
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    }

    public void configure() {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static ActiveMQProducer getInstance() {
        return instance;
    }

    public void addToQueue(String from, String text) {
        try {
            producer = session.createProducer(destination);
            MapMessage message = session.createMapMessage();
            message.setString("from", from);
            message.setString("text", text);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ActiveMQProducer producer = getInstance();
        producer.configure();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter your name: ");
            String from = scanner.nextLine();
            System.out.print("Enter your message: ");
            String text = scanner.nextLine();
            producer.addToQueue(from, text);
            System.out.print("Message successfully sent!\n");
            System.out.print("Do you want to send another message? (y / n) ");
        } while (!scanner.nextLine().equals("n"));
        producer.close();
    }

}