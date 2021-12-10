package com.softserve.bot.view.handler;

import com.softserve.bot.model.BotMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class UndefinedMessageHandler implements Handler {
    private final SendMessage sendMessage;
    private final BotMessages messages;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    @Override
    public SendMessage handle(Update update) {
        cleanRequests();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(messages.getUndefinedMassage());
        return sendMessage;
    }
}
