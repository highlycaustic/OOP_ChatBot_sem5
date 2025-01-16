package org.hcl.chatbot.oop_chatbot_sem5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatApplication extends Application {
    static ChatController currController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        currController = fxmlLoader.getController();
        scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
        stage.setMinWidth(540);
        stage.setMinHeight(340);
        stage.setWidth(540);
        stage.setHeight(340);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("chat-icon.png")));
        stage.setTitle("Простой ЧатБот");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        currController.Save();
    }

    public static void main(String[] args) {
        launch();
    }
}