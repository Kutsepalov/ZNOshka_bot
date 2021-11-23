package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.config.ButtonRegister;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class SpecializationHandler {
    private Message message;
    private SendMessage sendMessage;

    public void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handler(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        // прикрепть кнопки выбора специальности
        sendMessage.setText("Cпеціальності:");
        return sendMessage;
    }
}
