package repository;

import entities.Message;
import entities.Request;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexander on 07.07.2016.
 */
public class Repository {
    static private BlockingQueue<Message> pendingMessages = new LinkedBlockingQueue<Message>();
    static private BlockingQueue<Request> pendingRequests = new LinkedBlockingQueue<Request>();
    static private Message currentMessage;
    static private Thread requestHandler = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    currentMessage = pendingMessages.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Request> currentRequests = new ArrayList<Request>();

                while (pendingRequests.size() > 0) {
                    try {
                        currentRequests.add(pendingRequests.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

//                pendingRequests.clear();

                for (Request request : currentRequests) {
                    request.setResponse(currentMessage);
                }
            }
        }
    };

    static {
        requestHandler.start();
    }

    static public void submitRequest(Request request) {
//        for (Request req : pendingRequests) {
//            if (req.getSender().equals(request.getSender())) {
//                return;
//            }
//        }
        pendingRequests.add(request);
    }

    static public void submitMessage(Message message) {
        pendingMessages.add(message);
    }
}
