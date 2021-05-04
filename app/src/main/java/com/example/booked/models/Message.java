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

    //for firebase
    public Message() {
    }

    /**This constructor initializes receiver and sender ıd and message text.
     * @param senderId
     * @param receiverId
     * @param message refers to message text
     * */
    public Message(String receiverId, String senderId, String message) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
    }

    //Methods

    // accessor methods
    public String getMessage() {
        return message;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderId() {
        return senderId;
    }


    // mutator methods
    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
