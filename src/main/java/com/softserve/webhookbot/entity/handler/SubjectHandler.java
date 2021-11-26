package com.softserve.webhookbot.entity.handler;

import com.softserve.webhookbot.config.ButtonSubjectRegister;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.enumeration.EnumSetUtil;
import com.softserve.webhookbot.enumeration.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.EnumSet;

@AllArgsConstructor
@Component
public class SubjectHandler {
    private ButtonSubjectRegister buttonSubjectRegister;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private Message message;
    private SendMessage sendMessage;
    private AlertSender alertSender;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public EditMessageReplyMarkup deleteSelectedSubject(Update update, EnumSet<Subject> enumSet) {
        enumSet.clear();
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
        sendMessage.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, 0));
        sendMessage.setText("Усі предмети:");
        return sendMessage;
    }

    public BotApiMethod<? extends Serializable> setAndRemoveTick(Update update, Subject element, EnumSet<Subject> enumSet) {
        if (enumSet.contains(element)) {
            enumSet.remove(element);
            return processingSelectionSubject(update, enumSet);
        } else if (enumSet.size() < 5) {
            EnumSetUtil.radioButtonImpl(element, enumSet);
            return processingSelectionSubject(update, enumSet);
        } else {
            return alertSender.sendSubjectAlert(update);
        }
    }
}
