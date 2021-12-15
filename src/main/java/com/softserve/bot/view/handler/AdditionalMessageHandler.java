package com.softserve.bot.view.handler;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.view.sender.MainMenuSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class AdditionalMessageHandler implements Handler {
    private final MainMenuSender mainMenuSender;
    private final SendMessage sendMessage;
    private final BotMessages messages;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        Message message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(messages.getShowMenu());
        sendMessage.setReplyMarkup(mainMenuSender.getMenuReply());
        return sendMessage;
    }
}
