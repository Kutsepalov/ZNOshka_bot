package com.softserve.bot.view.sender;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class AlertSender {
    @Value("${telegrambot.alert.out-of-limit}")
    private String outOfLimit;
    @Value("${telegrambot.alert.not-enough}")
    private String notEnough;
     public AnswerCallbackQuery sendSubjectAlert(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(outOfLimit);
        return answerCallbackQuery;
    }

    public BotApiMethod<?> sendNotEnoughSubject(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(notEnough);
        return answerCallbackQuery;
    }
}
