package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.config.ButtonRegister;
import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@Component
public class TelegramFacade {
    private ButtonRegister buttonRegister;
    private Message message;
    private SendMessage sendMessage;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private EnumSet<Subject> enamSet = EnumSet.noneOf(Subject.class);

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            for (Subject value : Subject.values()) {
                Subject element = Subject.valueOf(update.getCallbackQuery().getData());
                if (setAndRemoveTick(update, value, element))
                    return (BotApiMethod<?>) editMessageReplyMarkup;
            }
            return null;// Выдать сообщение, я тебя не понимаю
        } else if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "/subjects":
                    return processingRequestSubject(update);
                case "/specialties":
                    return processingRequestSpecialties(update);
                case "/help":
                    return processingRequestHelp(update);
                case "/start":
                    return processingRequestStart(update);
                case "/contacts":
                    return processingRequestContacts(update);
                default:
                    //TODO
            }
        }
        return null;
    }

    private boolean setAndRemoveTick(Update update, Subject value, Subject element) {
        if (element.equals(value)) {
            if (enamSet.contains(element)) {
                enamSet.remove(element);
            } else {
                radioButtonImpl(element);
            }
            processingSelectionSubject(update);
            return true;
        }
        return false;
    }

    private void radioButtonImpl(Subject element) {
        switch (element) {
            case UKRAINIAN:
                enamSet.remove(Subject.LITERATURE);
                enamSet.add(element);
                break;
            case LITERATURE:
                enamSet.remove(Subject.UKRAINIAN);
                enamSet.add(element);
                break;
            case MATHPROFILE:
                enamSet.remove(Subject.MATHSTANDART);
                enamSet.add(element);
                break;
            case MATHSTANDART:
                enamSet.remove(Subject.MATHPROFILE);
                enamSet.add(element);
                break;
            case FRENCH:
                enamSet.remove(Subject.ENGLISH);
                enamSet.remove(Subject.GERMANY);
                enamSet.remove(Subject.SPANISH);
                enamSet.add(element);
                break;
            case ENGLISH:
                enamSet.remove(Subject.FRENCH);
                enamSet.remove(Subject.GERMANY);
                enamSet.remove(Subject.SPANISH);
                enamSet.add(element);
                break;
            case GERMANY:
                enamSet.remove(Subject.FRENCH);
                enamSet.remove(Subject.ENGLISH);
                enamSet.remove(Subject.SPANISH);
                enamSet.add(element);
                break;
            case SPANISH:
                enamSet.remove(Subject.GERMANY);
                enamSet.remove(Subject.FRENCH);
                enamSet.remove(Subject.ENGLISH);
                enamSet.add(element);
                break;
            default:
                enamSet.add(element);
        }
    }

    private void processingSelectionSubject(Update update) {
        buttonRegister = new ButtonRegister();
        editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enamSet));
    }

    private SendMessage processingRequestContacts(Update update) {
        buttonRegister = new ButtonRegister();
        message = update.getMessage();
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Контакти:");
        return sendMessage;
    }

    private SendMessage processingRequestStart(Update update) {
        buttonRegister = new ButtonRegister();
        message = update.getMessage();
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Стартовий месседж:");
        return sendMessage;
    }

    private SendMessage processingRequestSubject(Update update) {
        buttonRegister = new ButtonRegister();
        message = update.getMessage();
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttonRegister.getInlineSubjectButtons(enamSet));
        sendMessage.setText("Усі предмети:");
        return sendMessage;
    }

    private SendMessage processingRequestSpecialties(Update update) {
        buttonRegister = new ButtonRegister();
        message = update.getMessage();
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        // прикрепть кнопки выбора специальности
        sendMessage.setText("Cпеціальності:");
        return sendMessage;
    }

    private SendMessage processingRequestHelp(Update update) {
        buttonRegister = new ButtonRegister();
        message = update.getMessage();
        sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        // вытащить из файла правила пользования
        sendMessage.setText("Правила користування:");
        return sendMessage;
    }

}