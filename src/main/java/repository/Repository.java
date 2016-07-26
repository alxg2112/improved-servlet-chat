package repository;

import entities.Message;
import entities.Request;
import jms.ActiveMQConsumer;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class used as static storage and handler for user requests and messages.
 */
public class Repository {

    /**
     * Queue that contains messages that have been submitted, but not yet sent to the clients.
     */
    static private BlockingQueue<Message> pendingMessages = new LinkedBlockingQueue<Message>();

    /**
     * Queue that contains user request that have been submitted, but not yet responded to.
     */
    static private BlockingQueue<Request> pendingRequests = new LinkedBlockingQueue<Request>();

    /**
     * Last message taken from messages queue.
     */
    static private Message currentMessage;

    /**
     * Thread that takes a message from the queue and passes it to all pending user requests.
     */
    static private Thread requestHandler = new Thread() {
        @Override
        public void run() {
            while (true) {

                // Take message from the queue
                try {
                    currentMessage = pendingMessages.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Create array for current user requests
                ArrayList<Request> currentRequests = new ArrayList<Request>();

                // Transfer all user requests from queue to the array
                while (pendingRequests.size() > 0) {
                    try {
                        currentRequests.add(pendingRequests.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Send response for all acquired requests
                for (Request request : currentRequests) {
                    request.setResponse(currentMessage);
                    System.out.println("Dispatched message [" + currentMessage.toString() + "] to the " +
                            "sender [" + request.getSender() + "]");
                }
            }
        }
    };

    /**
     * Thread that consumes messages from ActiveMQ queue and submits it to the repository.
     */
    static private Thread jmsMessageConsumer = new Thread(new ActiveMQConsumer());

    // Static initializer that starts request handling and consumer threads
    static {
        requestHandler.start();
        jmsMessageConsumer.start();
    }

    /**
     * Method that submits new user request to the repository.
     *
     * @param request request to be submitted
     */
    static public void submitRequest(Request request) {
        pendingRequests.add(request);
        System.out.println("Submitted request from sender [" + request.getSender() +
                "] to the repository. Current number of requests: " +
                pendingRequests.size());
    }

    /**
     * Method that submits new message to the repository.
     *
     * @param message message to be submitted.
     */
    static public void submitMessage(Message message) {
        pendingMessages.add(message);
        System.out.println("Submitted message to the repository. Current number of messages: " +
                pendingMessages.size());
    }
}
