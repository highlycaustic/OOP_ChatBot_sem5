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
     * компилируется в конструкторе {@link BotModel}.*/
    private final Pattern arithmOperPattern;
    /** {@link Pattern} регулярного выражения для поиска приветствия по типу "Привет" или "Здравствуйте",
     * компилируется в конструкторе {@link BotModel}.*/
    private final Pattern greetingPattern;
    /** {@link Pattern} регулярного выражения для поиска запроса на изменение имени пользователя,
     * компилируется в конструкторе {@link BotModel}.*/
    private final Pattern userNamingPattern;
    /** {@link Pattern} регулярного выражения для поиска запроса на изменение имени бота,
     * компилируется в конструкторе {@link BotModel}.*/
    private final Pattern botNamingPattern;
    /** Поле типа {@link String}, хранит имя пользователя, инициализируется в конструкторе {@link BotModel}.*/
    private String userName;
    /** Поле типа {@link String}, хранит имя бота, инициализируется в конструкторе {@link BotModel}.*/
    private String botName;

    /** Возвращает текущую историю сообщений, хранимую в объекте {@link BotModel}
     * @return Массив объектов {@link Message} */
    public ArrayList<Message> getChatHistory() {
        return this.ChatHistory;
    }

    /** Возвращает текущие имена пользователя и бота, хранимые в объекте {@link BotModel}
     * @return Массив {@link String} из двух значений: имя пользователя, имя бота*/
    public String[] getNames() {
        String[] names = new String[2];
        names[0] = this.userName;
        names[1] = this.botName;
        return names;
    }

    /** Задает имя пользователя
     * @param userName Имя пользователя типа {@link String}*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Задает имя бота
     * @param botName Имя бота типа {@link String}*/
    public void setBotName(String botName) {
        this.botName = botName;
    }

    /** Загружает из файла имена пользователя и бота и историю сообщений.
     * Записывает в соответствующие поля объекта {@link BotModel}
     * @throws Exception Если не удалось открыть файл*/
    public void loadBotData() throws Exception {
        dataObj fileData = BotSerialization.fileLoad();
        this.ChatHistory = fileData.messages;
        this.setUserName(fileData.names[0]);
        this.setBotName(fileData.names[1]);
    }

    /** Сохраняет текущие имена пользователя и бота и историю сообщений в файл
     * @throws IOException Если не удалось записать файл*/
    public void saveBotData() throws IOException {
        BotSerialization.fileSave(this.getNames(), this.getChatHistory());
    }

    /** Записывает в поля стандартные значения:
     * <p>userName = "Вы";
     * <p>botName = "ЧатБот";
     * <p>Создает пустой массив ChatHistory.*/
    public void newBotData() {
        this.userName = "Вы";
        this.botName = "ЧатБот";
        this.ChatHistory = new ArrayList<Message>();
    }

    /** Конструктор класса {@link BotModel}
     *
     * <p>Пытается загрузить файл с данными об именах и истории переписки, если ловит исключение {@link Exception},
     * то вызывает метод {@link BotModel#newBotData()}
     *
     * <p>Компилирует паттерны для регулярных выражений
     * {@link BotModel#arithmOperPattern},
     * {@link BotModel#greetingPattern},
     * {@link BotModel#userNamingPattern},
     * {@link BotModel#botNamingPattern}*/
    public BotModel() {
        try {
            loadBotData(); // Загрузить данные из файла
        }
        catch (Exception e) {
            System.out.println("Error loading datafile. Creating default instance");
            newBotData(); // Использовать стандартные данные, если загрузка не удалась
        }

        // Компиляция паттернов рег.выражений
        this.arithmOperPattern = Pattern.compile("^(?<a>\\-?\\d+\\.?\\d*)\\s*(?<oper>[+\\-/%]|\\*{1,2})\\s*(?<b>\\-?\\d+\\.?\\d*)$");
        this.greetingPattern = Pattern.compile("(?:[Пп]ривет|[Зз]дравствуй(?:те)?)+,?\\s*(?:[Бб]от)*.?");
        this.userNamingPattern = Pattern.compile("(?:[Мм]еня\\s*зовут|[Мм]о[её]\\s*имя)\\s*(?<name>(?:\\p{L}|[0-9_])+)");
        this.botNamingPattern = Pattern.compile("(?:[Тт]ебя\\s*зовут|[Тт]во[её]\\s*имя)\\s*(?<name>(?:\\p{L}|[0-9_])+)");

        // Вывод в консоль служебной информации
        System.out.println("username: " + this.userName);
        System.out.println("botname: " + this.botName);
        System.out.println("History size: " + ChatHistory.size());
        System.out.println("ChatBot instance is up");
    }

    /** Селектор ответов бота.
     *
     * <p>Возвращает ответ типа {@link String} в зависимости от соответствия ввода регулярному выражению.</p>
     *
     * <p>Выбирает ответ для математических выражений, запросов смены имени пользователя или бота, приветствия.</p>
     *
     * @param input Ввод типа {@link String}
     * @return Ответ бота типа {@link String}*/
    private String botReplySelector(String input) {
        Matcher arithmOperMatcher = arithmOperPattern.matcher(input);
        Matcher greetingMatcher = greetingPattern.matcher(input);
        Matcher userNamingMatcher = userNamingPattern.matcher(input);
        Matcher botNamingMatcher = botNamingPattern.matcher(input);

        // Математические выражения
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

        // Смена имени пользователя
        if (userNamingMatcher.matches()) {
            String newUserName = userNamingMatcher.group("name");
            this.userName = newUserName;
            return "Теперь я буду называть вас " + newUserName;
        }

        // Смена имени бота
        if (botNamingMatcher.matches()) {
            String newBotName = botNamingMatcher.group("name");
            this.botName = newBotName;
            return "Теперь меня зовут " + newBotName;
        }

        // Ответ на приветствие
        if (greetingMatcher.matches()){
            return "Привет!";
        }

        // Если совпадений не обнаружено, стандартный ответ
        return "Мне нечего ответить на ваш запрос";
    }

    /** Добавляет сообщение типа {@link Message} к концу текущего массива {@link BotModel#ChatHistory}.
     * @param author Имя отправителя типа {@link String}
     * @param text Текст сообщения типа {@link String}*/
    public void registerMessage(String author, String text) {
        Message message = new Message(author, text);
        ChatHistory.add(message);
    }

    /** Обработка ботом сообщения от пользователя.
     * @param input Текст сообщения пользователя типа {@link String}
     * @return Массив типа {@link String} из двух строк, являющихся представлением сообщений пользователя и бота*/
    public String[] reply(String input) {
        // Записать сообщения пользователя и ответ бота в ChatHistory
        registerMessage(userName, input);
        registerMessage(botName, botReplySelector(input));
        // Получить из ChatHistory только что записанные сообщения
        Message lastUserMessage = ChatHistory.get(ChatHistory.size() - 2);
        Message lastBotMessage = ChatHistory.get(ChatHistory.size() - 1);
        // Вернуть массив из 2 строк
        return new String[] {lastUserMessage.Display(), lastBotMessage.Display()};
    }
}