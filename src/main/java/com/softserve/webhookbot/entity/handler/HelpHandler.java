package com.softserve.webhookbot.entity.handler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
@RequiredArgsConstructor
@Component
public class HelpHandler{
    private final SendMessage sendMessage;
    @Value("${telegrambot.message.help}")
    private String help;
    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        Message message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        //TODO вытащить из файла правила пользования
        sendMessage.setText(help);
        return sendMessage;
    }
}
