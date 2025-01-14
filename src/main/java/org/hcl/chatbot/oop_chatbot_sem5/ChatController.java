package org.hcl.chatbot.oop_chatbot_sem5;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    BotModel currBot = new BotModel();

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Label statusText;

    @FXML
    private TextField textInputField;

    @FXML
    protected void onSendButtonClick() {
        String message = textInputField.getText();
        String reply = currBot.botMessage(textInputField.getText());
        statusText.setText("Сообщение отправлено");
        messageTextArea.appendText("[" + ChatTimestamp.currTime() + "] " + "Вы: " + message + "\r\n");
        messageTextArea.appendText("[" + ChatTimestamp.currTime() + "] " + "ЧатБот: " + reply + "\r\n");
        textInputField.clear();

    }
}