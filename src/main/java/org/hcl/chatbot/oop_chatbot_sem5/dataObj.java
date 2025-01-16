package org.hcl.chatbot.oop_chatbot_sem5;

import java.util.ArrayList;

public class dataObj {
    public String[] names;
    public ArrayList<Message> messages;

    public dataObj(String[] names, ArrayList<Message> messages) {
        this.names = names;
        this.messages = messages;
    }
}
