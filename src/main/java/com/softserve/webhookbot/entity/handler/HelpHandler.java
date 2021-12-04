package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.entity.BotMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class HelpHandler{
    private Message message;
    private final SendMessage sendMessage;
    private final BotMessages botMessages;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(botMessages.getRules());
        return sendMessage;
    }
}
