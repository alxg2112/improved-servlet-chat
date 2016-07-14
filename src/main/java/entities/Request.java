package entities;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Class that represents user request, stores info about client who sent it and provides
 * method for acquiring new message from the repository and sending it back to the client.
 */
public class Request {

    /**
     * Id of client from whom the request has arrived.
     */
    private String sender;

    /**
     * Single-element blocking queue that used for storing message acquired from the repository.
     */
    private BlockingQueue<Message> message = new ArrayBlockingQueue<Message>(1);

    /**
     * Method that gets the response message from the repository.
     * @return response message
     */
    @Deprecated
    public Message getResponse() {
        Message responseMessage = null;
        try {
            responseMessage = message.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * Gets the {@code sender}.
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Dispatches response to the client using qiven {@code HttpServletResponse} object.
     * @param context servlet context used to notify one when the request is responded
     * @param response response used to dispatch the response to the client
     */
    public void dispatchResponse(final AsyncContext context, final HttpServletResponse response) {

        // Thread used to dispatch message to the client
        Thread responseHandler = new Thread() {
            @Override
            public void run() {
                try {

                    // Take message from the queue
                    Message msg = message.take();

                    // Dispatch the message
                    response.getWriter().write(msg.toString() + "<br>");
                    context.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        responseHandler.start();
    }

    /**
     * Method that sets response to the request.
     * @param response response message
     */
    public void setResponse(Message response) {
        message.add(response);
    }

    /**
     * Constructor.
     * @param from id of the client who sent the request
     */
    public Request(String from) {
        this.sender = from;
    }
}
