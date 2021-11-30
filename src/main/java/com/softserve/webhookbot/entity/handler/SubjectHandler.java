package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.util.ButtonSubjectRegister;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.enumeration.Subject;
import com.softserve.webhookbot.util.EnumSetUtil;
import com.softserve.webhookbot.util.RadioButton;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.swing.*;
import java.io.Serializable;
import java.util.EnumSet;

@AllArgsConstructor
@Component
public class SubjectHandler {
    private ButtonSubjectRegister buttonSubjectRegister;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private Message message;
    private SendMessage sendMessage;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public EditMessageReplyMarkup deleteSelectedSubject(Update update, EnumSet<Subject> enumSet) {
        enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);
        return processingSelectionSubject(update, enumSet);
    }

    private EditMessageReplyMarkup processingSelectionSubject(Update update, EnumSet<Subject> enumSet) {
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        return editMessageReplyMarkup;
    }

    public SendMessage handle(Update update, EnumSet<Subject> enumSet) {
        cleanRequests();
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        sendMessage.setText("Усі предмети:");
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
