package com.softserve.webhookbot.entity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class AlertSender {
     AnswerCallbackQuery sendAlert(Update update) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText("Помилка, не можно вибрати більше 5 предметів.");
        return answerCallbackQuery;
    }
}
