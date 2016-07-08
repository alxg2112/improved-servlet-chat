package entities;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Alexander on 07.07.2016.
 */
public class Request {
    private BlockingQueue<Message> message = new ArrayBlockingQueue<Message>(1);

    public Message getResponse() {
        Message response = null;
        try {
            response = message.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void setResponse(Message response) {
        message.add(response);
    }

    private String sender;

    public Request(String from) {
        this.sender = from;
    }
}
