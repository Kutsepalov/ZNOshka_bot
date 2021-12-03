package com.softserve.bot.view.handler;

import com.softserve.bot.model.BotMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Component
public class ContactsHandler{
    private Message message;
    private final SendMessage sendMessage;
    private final BotMessages botMessages;

    public SendMessage handle(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(botMessages.getContacts());
        return sendMessage;
    }
}
