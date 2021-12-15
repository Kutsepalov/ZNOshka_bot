package com.softserve.bot.view.handler;

import lombok.RequiredArgsConstructor;
import com.softserve.bot.model.BotMessages;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@RequiredArgsConstructor
@Component
public class ContactsHandler implements Handler {
    private final SendMessage sendMessage;
    private final BotMessages botMessages;

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
