package com.softserve.webhookbot.entity.sender;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class AlertSender {
     public AnswerCallbackQuery sendSubjectAlert(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("Помилка, не можно вибрати більше 5 предметів.");
        return answerCallbackQuery;
    }

    public BotApiMethod<?> sendVersionAlert(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("Помилка, використайте останню запитану версію.");
        return answerCallbackQuery;

    }

    public BotApiMethod<?> sendNotEnoughSubject(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("Помилка, треба вибрати не менше 3 предметів.");
        return answerCallbackQuery;
    }
}
