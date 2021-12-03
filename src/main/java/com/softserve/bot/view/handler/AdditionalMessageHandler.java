package com.softserve.bot.view.handler;

import com.softserve.bot.view.sender.MainMenuSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@AllArgsConstructor
@Component
public class AdditionalMessageHandler {
    private MainMenuSender mainMenuSender;
    private Message message;
    private SendMessage sendMessage;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Скористайтеся головним меню.");
        sendMessage.setReplyMarkup(mainMenuSender.getMenuReply());
        return sendMessage;
    }
}
