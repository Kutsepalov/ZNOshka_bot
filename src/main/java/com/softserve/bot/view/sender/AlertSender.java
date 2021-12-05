package com.softserve.bot.view.sender;

import com.softserve.bot.model.BotMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Component
public class AlertSender {

    private BotMessages messages;
     public AnswerCallbackQuery sendSubjectAlert(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(messages.getTooManyAlert());
        return answerCallbackQuery;
    }

    public BotApiMethod<?> sendNotEnoughSubject(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(messages.getNotEnoughAlert());
        return answerCallbackQuery;
    }
}
