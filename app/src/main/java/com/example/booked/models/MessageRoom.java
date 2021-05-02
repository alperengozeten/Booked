package com.example.booked.models;

import java.util.ArrayList;

public class MessageRoom {

    private String senderId;
    private String receiverId;
    private ArrayList<Message> messages;

    public MessageRoom() {
    }

    public MessageRoom(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        messages = new ArrayList<Message>();
    }


    public void addMessage(Message m)
    {
        messages.add(m);
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
