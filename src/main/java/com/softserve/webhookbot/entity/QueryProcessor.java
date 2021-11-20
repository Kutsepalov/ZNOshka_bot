package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.config.ButtonRegister;
import com.softserve.webhookbot.enumeration.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@AllArgsConstructor
@Component
public class QueryProcessor {
    private ButtonRegister buttonRegister;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private Message message;
    private SendMessage sendMessage;


    EditMessageReplyMarkup processingSelectionSubject(Update update, EnumSet<Subject> enumSet) {
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
            editMessageReplyMarkup.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enumSet, enumSet.size()));
            return editMessageReplyMarkup;
    }

    SendMessage processingRequestContacts(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Контакти:");
        return sendMessage;
    }

    SendMessage processingRequestStart(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Стартовий месседж:");
        return sendMessage;
    }

    SendMessage processingRequestSubject(Update update, EnumSet<Subject> enumSet) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enumSet, 0));
        sendMessage.setText("Усі предмети:");
        return sendMessage;
    }

    SendMessage processingRequestSpecialties(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        // прикрепть кнопки выбора специальности
        sendMessage.setText("Cпеціальності:");
        return sendMessage;
    }

    SendMessage processingRequestHelp(Update update) {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        // вытащить из файла правила пользования
        sendMessage.setText("Правила користування:");
        return sendMessage;
    }

    void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }
}
