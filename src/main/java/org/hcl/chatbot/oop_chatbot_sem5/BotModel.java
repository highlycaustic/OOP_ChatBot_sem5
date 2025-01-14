package org.hcl.chatbot.oop_chatbot_sem5;

import java.util.ArrayList;
import java.util.regex.*;

public class BotModel {// TODO: сделать поле для истории сообщений прям в боте
    private ArrayList<String> ChatHistory;       // История переписки, массив из строк
    private final Pattern arithmOperPattern;    // Рег.выражение поиска арифметического выражения, ищет комбинацию двух чисел, разделенную оператором и пробелами
    private final Pattern greetingPattern;      // Рег.выражение поиска "Привет" или "Здравствуйте"

    public BotModel() {
        this.ChatHistory = new ArrayList<String>();
        // TODO: прикрутить сюда загрузку из файла методом класса файл хендлер
        this.arithmOperPattern = Pattern.compile("^(?<a>\\-?\\d+\\.?\\d*)\\s*(?<oper>[+\\-/%]|\\*{1,2})\\s*(?<b>\\-?\\d+\\.?\\d*)$");
        System.out.println("Arithmetic pattern compiled");

        this.greetingPattern = Pattern.compile("(?:[Пп]ривет|[Зз]дравствуй(?:те)?)+,?\s*(?:[Бб]от)*.?");
        System.out.println("Greeting pattern compiled");

        System.out.println("ChatBot instance is up");
    }

    private String botReplySelector(String input) {
        Matcher arithmOperMatcher = arithmOperPattern.matcher(input);
        Matcher greetingMatcher = greetingPattern.matcher(input);

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
        if (greetingMatcher.matches()){     // Ответ на приветствие
            return "Привет!";
        }
        return "Мне нечего ответить на ваш запрос";
    }

    public String botMessage(String input){
        ChatHistory.add(input);
        String botReply = botReplySelector(input);
        ChatHistory.add(botReply);
        return botReply;
    }
}
