package org.hcl.chatbot.oop_chatbot_sem5;

import java.text.SimpleDateFormat;

public class ChatTimestamp { // TODO: комментарии
    public static String currTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(System.currentTimeMillis());
    }
}
