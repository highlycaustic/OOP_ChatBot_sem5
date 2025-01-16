package org.hcl.chatbot.oop_chatbot_sem5;

import java.text.SimpleDateFormat;

public class ChatTimestamp { // TODO: комментарии
    public static String currTime(String mode){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        if (mode.equals("brackets")) {
            return "[" + timeFormat.format(System.currentTimeMillis()) + "]";
        }
        return timeFormat.format(System.currentTimeMillis());
    }
}
