package org.hcl.chatbot.oop_chatbot_sem5;

import java.io.Serializable;

public class Message implements Serializable {

    private String timestamp;
    private String author;
    private String text;

    public String getTimestamp() {
        return this.timestamp;
    }
    public String getAuthor() {
        return this.author;
    }
    public String getText() {
        return this.text;
    }

    public Message(String author, String text) {
        this.timestamp = ChatTimestamp.currTime("brackets");
        this.author = author;
        this.text = text;
    }
    public String Display() {
        return this.getTimestamp() + " " + this.getAuthor() + ": " + this.getText();
    }

}
