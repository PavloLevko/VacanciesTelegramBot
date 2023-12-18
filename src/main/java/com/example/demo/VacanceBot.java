package com.example.demo;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class VacanceBot extends TelegramLongPollingBot {


    public VacanceBot() {
        super("6957902060:AAGh5-yov6y_cx7nTUWExq_y1Rc6Mt79iuQ");
    }

    @Override
    public void onUpdateReceived(Update update) {
       String inputText = update.getMessage().getText();
        System.out.println("User input text: " + inputText);
        System.out.println("event");

    }


    @Override
    public String getBotUsername() {
        return "Vacancies_UA_bot";
    }
}
