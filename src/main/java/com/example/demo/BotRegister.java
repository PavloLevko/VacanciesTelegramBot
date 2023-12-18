package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotRegister {
    public final VacanceBot vacanceBot;
    public BotRegister(VacanceBot vacanceBot){
        this.vacanceBot = vacanceBot;
    }
    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi= new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(vacanceBot);
    }
}
