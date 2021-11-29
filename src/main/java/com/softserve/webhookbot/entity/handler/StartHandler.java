package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.entity.sender.MainMenuSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@AllArgsConstructor
@Component
public class StartHandler{
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
        sendMessage.setText("Стартовий месседж:");
        sendMessage.setReplyMarkup(mainMenuSender.getMenuReply());
        return sendMessage;
    }
}
