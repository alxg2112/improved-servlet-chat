package entities;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Alexander on 07.07.2016.
 */
public class Request {
    private String sender;
    private BlockingQueue<Message> message = new ArrayBlockingQueue<Message>(1);

    public Message getResponse() {
        Message responseMessage = null;
        try {
            responseMessage = message.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }

    public void sendResponse(final HttpServletResponse response) {
//        Message responseMessage = null;
//        try {
//            responseMessage = message.take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return responseMessage;
        Thread responseHandler = new Thread() {
            @Override
            public void run() {
                try {
                    Message msg = message.take();
                    response.getWriter().write(msg.toString() + "<br>");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        responseHandler.start();
    }

    public void setResponse(Message response) {
        message.add(response);
    }

    public Request(String from) {
        this.sender = from;
    }
}
