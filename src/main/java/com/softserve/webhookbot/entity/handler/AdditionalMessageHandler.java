package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.entity.sender.MainMenuSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
public class AdditionalMessageHandler {
    private final MainMenuSender mainMenuSender;
    private final SendMessage sendMessage;
    @Value("${telegrambot.message.show-menu}")
    private String menu;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        Message message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(menu);
        sendMessage.setReplyMarkup(mainMenuSender.getMenuReply());
        return sendMessage;
    }
}
