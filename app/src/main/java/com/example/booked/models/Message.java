package com.example.booked.models;

/**
 * This is the Message model class. Message is used by users to communicate
 */
public class Message {

    //Properties
    private String receiverId;
    private String senderId;
    private String message;

    //Constructors
    public Message() {
    }

    public Message(String receiverId, String senderId, String message) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
    }

    //Methods

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
