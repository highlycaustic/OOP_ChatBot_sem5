package org.hcl.chatbot.oop_chatbot_sem5;

import java.io.*;
import java.util.ArrayList;

public class BotSerialization {
    public static void fileSave(String[] namesArray, ArrayList<Message> chatHistory) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objects.dat"));
        out.writeObject(namesArray);
        out.writeObject(chatHistory);
        out.close();
    }

    public static dataObj fileLoad() throws Exception, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("objects.dat"));
        String[] names = (String[])in.readObject();
        ArrayList<Message> dataIn = (ArrayList<Message>)in.readObject();
        in.close();
        return new dataObj(names, dataIn);
    }
}

