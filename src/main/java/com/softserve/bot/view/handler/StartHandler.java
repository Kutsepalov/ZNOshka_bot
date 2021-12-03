package com.softserve.bot.view.handler;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.view.sender.MainMenuSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class StartHandler{
    private final MainMenuSender mainMenuSender;
    private Message message;
    private final SendMessage sendMessage;
    private final BotMessages botMessages;

    public SendMessage handle(Update update) {
        sendMessage.setReplyMarkup(null);
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(botMessages.getStart());
        sendMessage.setReplyMarkup(mainMenuSender.getMenuReply());
        return sendMessage;
    }
}
