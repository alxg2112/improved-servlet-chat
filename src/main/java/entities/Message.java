package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexander on 07.07.2016.
 */
public class Message {
    private String from;
    private String text;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        return from + " (" + dateString + ")" + ":\t" + text;
    }
}
