package com.softserve.webhookbot.entity.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import com.softserve.webhookbot.entity.BotMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@RequiredArgsConstructor
@Component
public class ContactsHandler{
    private final SendMessage sendMessage;
    private final BotMessages botMessages;
    @Value("${telegrambot.message.contacts}")
    private String contacts;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        Message message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(botMessages.getContacts());
        return sendMessage;
    }
}
