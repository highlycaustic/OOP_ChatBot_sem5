package org.hcl.chatbot.oop_chatbot_sem5;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/** Класс контроллера приложения*/
public class ChatController {

    BotModel currBot = new BotModel(); // Инициализировать экземпляр бота

    /** Поле вывода сообщений*/
    @FXML
    private TextArea messageTextArea;

    /** Поле ввода сообщения*/
    @FXML
    private TextField textInputField;

    /** Инициализация FXML, вызывается при загрузке контроллера. Загружает данные бота при открытии приложения*/
    public void initialize() {
        this.Load();
    }

    /** Заполняет поле вывода текста сообщениями из {@link BotModel#getChatHistory()} */
    private void populateTextArea() {
        ArrayList<Message> chatHistory= currBot.getChatHistory();
        for (Message message : chatHistory) {
            messageTextArea.appendText(message.Display() + "\r\n");
        }
    }

    /** Вызов {@link BotModel#newBotData()} и очистка поля вывода сообщений*/
    @FXML
    protected void New() {
        currBot.newBotData();
        messageTextArea.clear();
    }

    /** Загружает данные из бота и отрисовывает их методом {@link ChatController#populateTextArea()}*/
    @FXML
    protected void Load() {
        try {
            messageTextArea.clear();
            currBot.loadBotData();
            populateTextArea();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    /** Сохранение данных бота*/
    @FXML
    protected void Save() {
        try {
            currBot.saveBotData();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    /** Отправка сообщения боту. Получает строку типа {@link String} из поля ввода {@link ChatController#textInputField}
     * и отправляет ее боту для получения ответа. Сообщение пользователя и ответ бота добавляются к полю вывода текста*/
    @FXML
    protected void onSendButtonClick() {
        String userInput = textInputField.getText();
        String[] reply = currBot.reply(userInput);

        messageTextArea.appendText(reply[0] + "\r\n");
        messageTextArea.appendText(reply[1] + "\r\n");

        textInputField.clear();
    }
}