package org.hcl.chatbot.oop_chatbot_sem5;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ChatController {

    BotModel currBot = new BotModel();

    public void initialize() {
        this.Load();
    }

    private void populateTextArea() {
        ArrayList<Message> chatHistory= currBot.getChatHistory();
        for (Message message : chatHistory) {
            messageTextArea.appendText(message.Display() + "\r\n");
        }
    }

    @FXML
    private TextArea messageTextArea;

    @FXML
    private TextField textInputField;

    @FXML
    protected void New() {
        currBot.newBotData();
        messageTextArea.clear();
    }

    @FXML
    protected void Load() {
        try {
            messageTextArea.clear();
            currBot.loadBotData();
            populateTextArea();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    protected void Save() {
        try {
            currBot.saveBotData();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    protected void onSendButtonClick() {
        String userInput = textInputField.getText();
        String[] reply = currBot.reply(userInput);

        messageTextArea.appendText(reply[0] + "\r\n");
        messageTextArea.appendText(reply[1] + "\r\n");

        textInputField.clear();
    }
}