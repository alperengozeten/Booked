package com.example.booked.models;

import java.util.ArrayList;

/**
 * Messageroom is used by users to communicate with each other about posts
 */
public class MessageRoom {

    //Properties
    private String senderId;
    private String receiverId;
    private ArrayList<Message> messages;

    //Constructors
    /**
     * for firebase
     */
    public MessageRoom() {
    }

    /**This constructor initializes id's of users messaging.
     * @param senderId
     * @param receiverId
     *
     * */
    public MessageRoom(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        messages = new ArrayList<Message>();
    }

    //Methods
    /** This method adds a new message.
     * @param m
     * */
    public void addMessage(Message m)
    {
        messages.add(m);
    }

    //accessor methods

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    //mutator methods

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
