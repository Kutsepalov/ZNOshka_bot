package com.softserve.bot.view.handler;

import com.softserve.bot.controller.MailingController;
import com.softserve.bot.service.database.UserService;
import com.softserve.bot.util.AdminButtonRegister;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@AllArgsConstructor()
@Component
public class AdminHandler implements Handler{
    private final UserService userService;
    private final MailingController mailing;

    @Override
    public SendMessage handle(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(AdminButtonRegister.getAdminKeyboard());
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Виберіть операцію:");
        return sendMessage;
    }

    private SendMessage handleReturn(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(AdminButtonRegister.getAdminKeyboard());
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Виберіть операцію:");
        return sendMessage;
    }

    public SendMessage handleSend(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Напишіть повідомлення:");

        return sendMessage;
    }

    public SendMessage handleConfirmed(Update update, Map<String, String> callback){
        if(callback.get("text").equals("Відмінити")){
            return handleReturn(update);
        }
        String text = update.getCallbackQuery().getMessage().getText();
        var users = userService.list();

        mailing.mailingAllUsers(text, users);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Повідомлення відправлено");

        return sendMessage;
    }

    public SendMessage handleConfirmation(Update update){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(update.getMessage().getText());
        sendMessage.setReplyMarkup(AdminButtonRegister.getConfirmationKeyboard());
        return sendMessage;
    }

}
