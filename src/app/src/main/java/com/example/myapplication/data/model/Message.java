package com.example.myapplication.data.model;

public class Message {
    int senderId;
    int receiverId;
    String msg = "";

    public Message(int senderId, int receiverId, String msg) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msg = msg;
    }

    public String getMsg(String uname) {
        return uname + ": " + msg;
    }

}
