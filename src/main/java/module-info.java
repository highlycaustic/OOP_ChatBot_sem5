module org.hcl.chatbot.oop_chatbot_sem5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.hcl.chatbot.oop_chatbot_sem5 to javafx.fxml;
    exports org.hcl.chatbot.oop_chatbot_sem5;
}