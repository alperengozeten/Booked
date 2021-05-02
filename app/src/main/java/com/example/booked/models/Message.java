package com.example.booked.models;

public class Message {

    private String receiverId;
    private String senderId;
    private String message;


    public Message() {
    }

    public Message(String receiverId, String senderId, String message) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
    }

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
