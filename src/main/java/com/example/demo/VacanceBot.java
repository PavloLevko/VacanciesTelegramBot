package com.example.demo;

import com.example.demo.dto.VacancyDto;
import com.example.demo.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VacanceBot extends TelegramLongPollingBot {
    @Autowired
    private VacancyService vacancyService;

    private final Map<Long, String> lastShowVacanciesLevel = new HashMap<>();

    public VacanceBot() {
        super("6957902060:AAGh5-yov6y_cx7nTUWExq_y1Rc6Mt79iuQ");
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.getMessage() != null) {
                handleStartCommand(update);
            }
            if (update.getCallbackQuery() != null) {
                String callbackData = update.getCallbackQuery().getData();
                if ("showJuniorVacancies".equals(callbackData)) {
                    showJuniorVacancies(update);
                } else if ("showMiddleVacancies".equals(callbackData)) {
                    showMiddleVacancies(update);
                } else if ("showSeniorVacancies".equals(callbackData)) {
                    showSeniorVacancies(update);
                } else if (callbackData.startsWith("vacancyId=")) {
                    String id = callbackData.split("=")[1];
                    showVacancyDescription(id, update);
                } else if ("backToVacancies".equals(callbackData)) {
                    handleBackToVacanciesCommand(update);
                } else if ("backToStartMenu".equals(callbackData)) {
                    handleBackToStartMenu(update);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't send message to user!", e);
        }
    }

    private void handleBackToStartMenu(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Choose title: ");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getStartMenu());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleBackToVacanciesCommand(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String level = lastShowVacanciesLevel.get(chatId);
        if ("junior".equals(level)) {
            showJuniorVacancies(update);
        } else if ("middle".equals(level)) {
            showMiddleVacancies(update);
        } else if ("senior".equals(level)) {
            showSeniorVacancies(update);
        }
    }

    private void showVacancyDescription(String id, Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        VacancyDto vacancyDto = vacancyService.get(id);
        String description = vacancyDto.getShortDescription();
        sendMessage.setText(description);
        sendMessage.setReplyMarkup(getBackToVacanciesMenu());
        execute(sendMessage);
    }

    private ReplyKeyboard getBackToVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton backToVacanciesButton = new InlineKeyboardButton();
        backToVacanciesButton.setText("Back to vacancies");
        backToVacanciesButton.setCallbackData("backToVacancies");
        row.add(backToVacanciesButton);

        InlineKeyboardButton backToStartMenuButton = new InlineKeyboardButton();
        backToStartMenuButton.setText("Back to Menu");
        backToStartMenuButton.setCallbackData("backToStartMenu");
        row.add(backToStartMenuButton);

        InlineKeyboardButton chatGPTButon = new InlineKeyboardButton();
        chatGPTButon.setText("Chat GPT");
        chatGPTButon.setUrl("https://chat.openai.com/");
        row.add(chatGPTButon);

        return new InlineKeyboardMarkup(List.of(row));
    }

    private void handleStartCommand(Update update) {
        String inputText = update.getMessage().getText();
        System.out.println("User input text: " + inputText);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Hello! Please, choose your title: ");
        sendMessage.setReplyMarkup(getStartMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private ReplyKeyboard getStartMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton junior = new InlineKeyboardButton();
        junior.setText("Junior");
        junior.setCallbackData("showJuniorVacancies");
        row.add(junior);

        InlineKeyboardButton middle = new InlineKeyboardButton();
        middle.setText("Middle");
        middle.setCallbackData("showMiddleVacancies");
        row.add(middle);

        InlineKeyboardButton senior = new InlineKeyboardButton();
        senior.setText("Senior");
        senior.setCallbackData("showSeniorVacancies");
        row.add(senior);

        InlineKeyboardMarkup keyboardButton = new InlineKeyboardMarkup();
        keyboardButton.setKeyboard(List.of(row));

        return keyboardButton;
    }

    private void showJuniorVacancies(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);

        sendMessage.setReplyMarkup(getJuniorVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        lastShowVacanciesLevel.put(chatId, "junior");
    }

    private void showMiddleVacancies(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getMiddleVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        lastShowVacanciesLevel.put(chatId, "middle");
    }

    private void showSeniorVacancies(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(getSeniorVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        lastShowVacanciesLevel.put(chatId, "senior");
    }

    private ReplyKeyboard getVacanciesMenu(List<VacancyDto> vacancyDtoList) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (VacancyDto vacancyDto : vacancyDtoList) {
            InlineKeyboardButton vacanciesButton = new InlineKeyboardButton();
            vacanciesButton.setText(vacancyDto.getTitle());
            vacanciesButton.setCallbackData("vacancyId=" + vacancyDto.getId());
            row.add(vacanciesButton);
        }

        InlineKeyboardMarkup keyboardButton = new InlineKeyboardMarkup();
        keyboardButton.setKeyboard(List.of(row));
        return keyboardButton;
    }

    private ReplyKeyboard getMiddleVacanciesMenu() {
        List<VacancyDto> vacancyDtoList = vacancyService.getMiddleVacancies();

        return getVacanciesMenu(vacancyDtoList);

    }

    private ReplyKeyboard getJuniorVacanciesMenu() {
        List<VacancyDto> vacancyDtoList = vacancyService.getJuniorVacancies();

        return getVacanciesMenu(vacancyDtoList);
    }


    private ReplyKeyboard getSeniorVacanciesMenu() {
        List<VacancyDto> vacancyDtoList = vacancyService.getSeniorVacancies();

        return getVacanciesMenu(vacancyDtoList);
    }


    @Override
    public String getBotUsername() {
        return "Vacancies_UA_bot";
    }
}
