package jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import repository.Repository;

import javax.jms.*;

/**
 * Consumer class that consumes messages from ActiveMQ queue and submits them to the repository.
 */
public class ActiveMQConsumer implements Runnable, ExceptionListener {
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("MessageQueue");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new ConsumerMessageListener());
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occurred. Shutting down client.");
    }

    /**
     * Listener class that listens for incoming messages in ActiveMQ queue and submits them to the repository.
     */
    private static class ConsumerMessageListener implements MessageListener {
        public void onMessage(javax.jms.Message message) {
            MapMessage mapMessage = (MapMessage) message;
            try {
                String from = mapMessage.getString("from");
                String text = mapMessage.getString("text");
                Repository.submitMessage(new entities.Message(from, text));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
