package org.hcl.chatbot.oop_chatbot_sem5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;


/**
 * Класс ЧатБота
*/
public class BotModel {
    /** Поле, содержащее массив из объектов класса {@link Message}, хранит историю переписки.*/
    private ArrayList<Message> ChatHistory;
    /** {@link Pattern} регулярного выражения для поиска арифметических выражений,
     * компилируется в конструкторе {@link BotModel} */
    private final Pattern arithmOperPattern;
    /** {@link Pattern} регулярного выражения для поиска приветствия по типу "Привет" или "Здравствуйте",
     * компилируется в конструкторе {@link BotModel} */
    private final Pattern greetingPattern;      // Рег.выражение поиска "Привет" или "Здравствуйте"
    /** {@link Pattern} регулярного выражения для поиска запроса на изменение имени пользователя,
     * компилируется в конструкторе {@link BotModel} */
    private final Pattern userNamingPattern;
    /** {@link Pattern} регулярного выражения для поиска запроса на изменение имени бота,
     * компилируется в конструкторе {@link BotModel} */
    private final Pattern botNamingPattern;
    /** Поле типа {@link String}, хранит имя пользователя, инициализируется в конструкторе */
    private String userName;
    private String botName;

    public ArrayList<Message> getChatHistory() {
        return this.ChatHistory;
    }

    public String[] getNames() {
        String[] names = new String[2];
        names[0] = this.userName;
        names[1] = this.botName;
        return names;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void loadBotData() throws Exception {
        dataObj fileData = BotSerialization.fileLoad();
        this.ChatHistory = fileData.messages;
        this.setUserName(fileData.names[0]);
        this.setBotName(fileData.names[1]);
    }

    public void saveBotData() throws IOException {
        BotSerialization.fileSave(this.getNames(), this.getChatHistory());
    }

    public void newBotData() {
        this.userName = "Вы";
        this.botName = "ЧатБот";
        this.ChatHistory = new ArrayList<Message>();
    }

    /** Конструктор класса.*/
    public BotModel() {
        try {
            loadBotData();
        }
        catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error loading datafile. Creating default instance");
            newBotData();
        }

        this.arithmOperPattern = Pattern.compile("^(?<a>\\-?\\d+\\.?\\d*)\\s*(?<oper>[+\\-/%]|\\*{1,2})\\s*(?<b>\\-?\\d+\\.?\\d*)$");
        this.greetingPattern = Pattern.compile("(?:[Пп]ривет|[Зз]дравствуй(?:те)?)+,?\\s*(?:[Бб]от)*.?");
        this.userNamingPattern = Pattern.compile("(?:[Мм]еня\\s*зовут|[Мм]о[её]\\s*имя)\\s*(?<name>(?:\\p{L}|[0-9_])+)");
        this.botNamingPattern = Pattern.compile("(?:[Тт]ебя\\s*зовут|[Тт]во[её]\\s*имя)\\s*(?<name>(?:\\p{L}|[0-9_])+)");

        System.out.println("username: " + this.userName);
        System.out.println("botname: " + this.botName);
        System.out.println("History size: " + ChatHistory.size());
        System.out.println("ChatBot instance is up");
    }

    private String botReplySelector(String input) {
        Matcher arithmOperMatcher = arithmOperPattern.matcher(input);
        Matcher greetingMatcher = greetingPattern.matcher(input);
        Matcher userNamingMatcher = userNamingPattern.matcher(input);
        Matcher botNamingMatcher = botNamingPattern.matcher(input);

        if (arithmOperMatcher.matches()){

            Float a = Float.parseFloat(arithmOperMatcher.group("a"));   // Перевод из String во Float для расчетов
            Float b = Float.parseFloat(arithmOperMatcher.group("b"));   //

            switch (arithmOperMatcher.group("oper")){   // Выбор операции
                case ("+"): return Float.toString(a + b);
                case ("-"): return Float.toString(a - b);
                case ("/"): return Float.toString(a / b);
                case ("%"): return Float.toString(a % b);
                case ("*"): return Float.toString(a * b);
                case ("**"): return Double.toString(Math.pow(a, b));
            }
        }

        if (userNamingMatcher.matches()) {
            String newUserName = userNamingMatcher.group("name");
            this.userName = newUserName;
            return "Теперь я буду называть вас " + newUserName;
        }

        if (botNamingMatcher.matches()) {
            String newBotName = botNamingMatcher.group("name");
            this.botName = newBotName;
            return "Теперь меня зовут " + newBotName;
        }

        if (greetingMatcher.matches()){     // Ответ на приветствие
            return "Привет!";
        }

        return "Мне нечего ответить на ваш запрос";
    }

    public void registerMessage(String author, String text) {
        Message message = new Message(author, text);
        ChatHistory.add(message);
    }

    public String[] reply(String input) {
        registerMessage(userName, input);
        registerMessage(botName, botReplySelector(input));
        Message lastUserMessage = ChatHistory.get(ChatHistory.size() - 2);
        Message lastBotMessage = ChatHistory.get(ChatHistory.size() - 1);
        //System.out.println("History size: " + ChatHistory.size());
        return new String[] {lastUserMessage.Display(), lastBotMessage.Display()};
    }

}