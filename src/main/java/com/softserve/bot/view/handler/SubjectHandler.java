package com.softserve.bot.view.handler;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.model.Subject;
import com.softserve.bot.util.ButtonSubjectRegister;
import com.softserve.bot.util.EnumSetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class SubjectHandler implements Handler {
    private final ButtonSubjectRegister buttonSubjectRegister;
    private final EditMessageReplyMarkup editMessageReplyMarkup;
    private final SendMessage sendMessage;
    private final BotMessages messages;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public EditMessageReplyMarkup deleteSelectedSubject(Update update, Set<Subject> enumSet) {
        enumSet.clear();
        enumSet.addAll(EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE));
        return processingSelectionSubject(update, enumSet);
    }

    private EditMessageReplyMarkup processingSelectionSubject(Update update, Set<Subject> enumSet) {
        editMessageReplyMarkup.setReplyMarkup(null);
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        return editMessageReplyMarkup;
    }


    public SendMessage handle(Update update) {
        cleanRequests();
        Set<Subject> enumSet = EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE);
        Message message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        sendMessage.setText(messages.getAllSubjects());
        return sendMessage;
    }

    public SendMessage handleReturn(Update update) {
        cleanRequests();
        Set<Subject> enumSet = EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE);
        Message message = update.getCallbackQuery().getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonSubjectRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
        sendMessage.setText(messages.getAllSubjects());
        return sendMessage;
    }

    public BotApiMethod<? extends Serializable> setAndRemoveTick(Update update, Subject element, Set<Subject> enumSet) {
        if (enumSet.contains(element)) {
            EnumSetUtil.removeTick(element, enumSet);
        } else {
            EnumSetUtil.addTick(element, enumSet);
        }
        return processingSelectionSubject(update, enumSet);
    }

}
