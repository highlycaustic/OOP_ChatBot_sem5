package org.hcl.chatbot.oop_chatbot_sem5;

public class BotModel {
    public String botReply(String input){
        if (input.equals("Тест")){
            return "Ответ";
        }
        return "Нет ответа";
    }
}
