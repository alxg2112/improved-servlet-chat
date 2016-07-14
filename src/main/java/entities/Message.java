package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that describes user message.
 */
public class Message {

    /**
     * Id of user who sent the message.
     */
    private String from;

    /**
     * Content of the message.
     */
    private String text;

    /**
     * Date and time of message arrival.
     */
    private Date date;

    /**
     * Constructor.
     * @param from id of user who sent the message
     * @param text content of the message
     */
    public Message(String from, String text) {
        this.from = from;
        this.text = text;
        date = new Date();
    }

    /**
     * Overriden {@code toString()} method, that returns string containing message info.
     * @return formatted string, containing id of user who sent the message, date it has
     * arrived and its content.
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        return from + " (" + dateString + ")" + ":\t" + text;
    }
}
