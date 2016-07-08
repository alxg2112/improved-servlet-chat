package entities;

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
        return from + ":\t" + text;
    }
}
