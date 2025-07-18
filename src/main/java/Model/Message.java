package Model;

import java.util.List;

/**
 * This is a class that models a Message.
 *
 * DO NOT CHANGE ANYTHING IN THIS CLASS!
 *
 */
public class Message {
    /**
     * An id for this message which will be automatically generated by the database.
     */
    public int message_id;
    /**
     * The id for the user who has posted this message. We will assume that this is provided by the front-end of this
     * application.
     */
    public int posted_by;
    /**
     * The text for this message- eg "this is my first post!". Must be not blank and under 255 characters
     */
    public String message_text;
    /**
     * The epoch time when this tweet was posted (number of seconds since Jan 1, 1970). Longs are large enough
     * to store this number. We will assume that this number is provided by the front-end of this application.
     */
    public long time_posted_epoch;
    /**
     * A default, no-args constructor, as well as correctly formatted getters and setters, are needed for
     * Jackson Objectmapper to work.
     */
    public Message(){
    }
    /**
     * When posting a new message, the id can be generated by the database. In that case, a constructor without
     * message_id is needed.
     * @param posted_by
     * @param message_text
     * @param time_posted_epoch
     */
    public Message(int posted_by, String message_text, long time_posted_epoch) {
        this.posted_by = posted_by;
        this.message_text = message_text;
        this.time_posted_epoch = time_posted_epoch;
    }
    /**
     * Whem retrieving a message from the database, all fields will be needed. In that case, a constructor with all
     * fields is needed.
     * @param message_id
     * @param posted_by
     * @param message_text
     * @param time_posted_epoch
     */
    public Message(int message_id, int posted_by, String message_text, long time_posted_epoch) {
        this.message_id = message_id;
        this.posted_by = posted_by;
        this.message_text = message_text;
        this.time_posted_epoch = time_posted_epoch;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return message_id
     */
    public int getMessage_id() {
        return message_id;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param message_id
     */
    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return posted_by
     */
    public int getPosted_by() {
        return posted_by;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param posted_by
     */
    public void setPosted_by(int posted_by) {
        this.posted_by = posted_by;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return message_text
     */
    public String getMessage_text() {
        return message_text;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param message_text
     */
    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return time_posted_epoch
     */
    public long getTime_posted_epoch() {
        return time_posted_epoch;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param time_posted_epoch
     */
    public void setTime_posted_epoch(long time_posted_epoch) {
        this.time_posted_epoch = time_posted_epoch;
    }
    /**
     * Overriding the default equals() method adds functionality to tell when two objects are identical, allowing
     * Assert.assertEquals and List.contains to function.
     * @param o the other object.
     * @return true if o is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return message_id == message.message_id && posted_by == message.posted_by
                && time_posted_epoch == message.time_posted_epoch && message_text.equals(message.message_text);
    }
    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", posted_by=" + posted_by +
                ", message_text='" + message_text + '\'' +
                ", time_posted_epoch=" + time_posted_epoch +
                '}';
    }
    public Message createMessage(Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMessage'");
    }
    public List<Message> getAllMessages() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMessages'");
    }
    public Message getMessageById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageById'");
    }
    public List<Message> getMessagesByUserId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesByUserId'");
    }
    public boolean deleteMessageById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMessageById'");
    }
    public Message updateMessage(int id, Message message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMessage'");
    }
    public int getId() {
        // TODO Auto-generated method stub
return message_id;
    }
}
