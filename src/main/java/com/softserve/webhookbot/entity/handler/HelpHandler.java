package com.softserve.webhookbot.entity.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class HelpHandler{
    private Message message;
    private SendMessage sendMessage;

    public SendMessage handler(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        //TODO вытащить из файла правила пользования
        sendMessage.setText("Правила користування:");
        return sendMessage;
    }
}
