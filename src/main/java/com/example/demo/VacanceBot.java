package com.example.demo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacanceBot extends TelegramLongPollingBot {


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
            }else if(callbackData.startsWith("vacancyId=")){
                    String id = callbackData.split("=")[1];
                    showVacancyDescription(id, update);
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Can't send message to user!", e);
        }



    }

    private void showVacancyDescription(String id, Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Vacancy description for vacancy with id = " + id);
        execute(sendMessage);
    }
   private void handleStartCommand (Update update) {
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
    private void showJuniorVacancies(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getJuniorVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMiddleVacancies(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getMiddleVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard getMiddleVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton maVacancy = new InlineKeyboardButton();
        maVacancy.setText("Middle Java developer at MA");
        maVacancy.setCallbackData("vacancyId=3");
        row.add(maVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Middle Java developer at Google");
        googleVacancy.setCallbackData("vacancyId=4");
        row.add(googleVacancy);

        InlineKeyboardMarkup keyboardButton = new InlineKeyboardMarkup();
        keyboardButton.setKeyboard(List.of(row));
        return keyboardButton;

    }

    private ReplyKeyboard getJuniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton maVacancy = new InlineKeyboardButton();
        maVacancy.setText("Junior Java developer at MA");
        maVacancy.setCallbackData("vacancyId=1");
        row.add(maVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Junior Java developer at Google");
        googleVacancy.setCallbackData("vacancyId=2");
        row.add(googleVacancy);

        InlineKeyboardMarkup keyboardButton = new InlineKeyboardMarkup();
        keyboardButton.setKeyboard(List.of(row));
        return keyboardButton;
    }
    private void showSeniorVacancies(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose vacancy");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getSeniorVacanciesMenu());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard getSeniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton monoVacancy = new InlineKeyboardButton();
        monoVacancy.setText("Senior Java developer at monoBank");
        monoVacancy.setCallbackData("vacancyId=5");
        row.add(monoVacancy);

        InlineKeyboardButton netflixVacancy = new InlineKeyboardButton();
        netflixVacancy.setText("Senior Java developer at Netflix");
        netflixVacancy.setCallbackData("vacancyId=6");
        row.add(netflixVacancy);

        InlineKeyboardMarkup keyboardButton = new InlineKeyboardMarkup();
        keyboardButton.setKeyboard(List.of(row));
        return keyboardButton;

    }


    @Override
    public String getBotUsername() {
        return "Vacancies_UA_bot";
    }
}
