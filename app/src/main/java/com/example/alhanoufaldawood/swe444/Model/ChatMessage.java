package com.example.alhanoufaldawood.swe444.Model;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageSender;
    private String messageRecever;

    public ChatMessage(String messageText, String messageSender, String messageRecever) {
        this.messageText = messageText;
        this.messageSender = messageSender;
        this.messageRecever = messageRecever;
    }

    public ChatMessage() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public String getMessageRecever() {
        return messageRecever;
    }

    public void setMessageRecever(String messageRecever) {
        this.messageRecever = messageRecever;
    }
}
