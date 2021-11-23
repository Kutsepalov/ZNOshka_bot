package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.config.ButtonRegister;
import com.softserve.webhookbot.enumeration.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@AllArgsConstructor
@Component
public class SubjectHandler {
    private ButtonRegister buttonRegister;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private Message message;
    private SendMessage sendMessage;

    public void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public EditMessageReplyMarkup processingSelectionSubject(Update update, EnumSet<Subject> enumSet) {
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        return editMessageReplyMarkup;
    }

    public SendMessage handle(Update update, EnumSet<Subject> enumSet) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enumSet, 0));
        sendMessage.setText("Усі предмети:");
        return sendMessage;
    }
}
