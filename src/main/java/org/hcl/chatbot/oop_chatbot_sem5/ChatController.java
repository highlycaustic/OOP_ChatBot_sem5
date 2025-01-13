package org.hcl.chatbot.oop_chatbot_sem5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChatController {

    BotModel currBot = new BotModel();

    @FXML
    private Label statusText;

    @FXML
    private Label replyText;

    @FXML
    private TextField textInputField;

    @FXML
    protected void onSendButtonClick() {
        statusText.setText("Сообщение отправлено");
        replyText.setText(currBot.botReply(textInputField.getText()));

    }
}