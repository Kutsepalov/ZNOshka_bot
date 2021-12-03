package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.util.ButtonSubjectRegister;
import com.softserve.webhookbot.enumeration.Subject;
import com.softserve.webhookbot.util.RadioButton;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.io.Serializable;
import java.util.EnumSet;

@AllArgsConstructor
@Component
public class SubjectHandler {
    private ButtonSubjectRegister buttonSubjectRegister;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private EditMessageText messageText;
    private Message message;
    private SendMessage sendMessage;

    @Value("${telegrambot.message.all-subjects}")
    private String allSubjects;
    @Value("${telegrambot.message.menu-instructions}")
    private String menuMessage;


    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public EditMessageReplyMarkup deleteSelectedSubject(Update update, EnumSet<Subject> enumSet) {
        enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);
        return processingSelectionSubject(update, enumSet);
    }

    private EditMessageReplyMarkup processingSelectionSubject(Update update, EnumSet<Subject> enumSet) {
        editMessageReplyMarkup.setReplyMarkup(null);
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        return editMessageReplyMarkup;
    }

    public EditMessageText deleteCurrentMarkup(Update update) {
        messageText.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        messageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        messageText.setText(menuMessage);
        return messageText;
    }

    public SendMessage handle(Update update, EnumSet<Subject> enumSet) {
        cleanRequests();
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        sendMessage.setText(allSubjects);
        return sendMessage;
    }

    public BotApiMethod<? extends Serializable> setAndRemoveTick(Update update, Subject element, EnumSet<Subject> enumSet) {
        if (enumSet.contains(element)) {
            RadioButton.removeMandatoryTick(element, enumSet);
            return processingSelectionSubject(update, enumSet);
        } else {
            RadioButton.radioButtonImpl(element, enumSet);
            return processingSelectionSubject(update, enumSet);
        }
    }

}
