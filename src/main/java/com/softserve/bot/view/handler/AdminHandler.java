package com.softserve.bot.view.handler;

import com.softserve.bot.util.AdminButtonRegister;
import com.softserve.bot.util.SpecialityButtonRegister;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@AllArgsConstructor
@Component
public class AdminHandler implements Handler{

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(AdminButtonRegister.getAdminKeyboard());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Виберіть операцію:");

        return sendMessage;
    }

    public SendMessage handleSend(Update update, Map<String, String> callback) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Напишіть повідомлення:");
        System.out.println(update.getCallbackQuery().getMessage().getText());

        return sendMessage;
    }

    public SendMessage handleText(Update update){
        SendMessage sendMessage = new SendMessage();
        /*Тут я достаю ID-шникики
        ОПА
        Тут я отправляю сообщения
        и тут что-то еще делаю
        */
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Повідомлення відправлено");

        return sendMessage;
    }


}
