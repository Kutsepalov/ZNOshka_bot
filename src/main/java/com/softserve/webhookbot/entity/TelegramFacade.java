package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.EnumSet;

@Component
public class TelegramFacade {
    private AlertSender alertSender;
    private QueryProcessor queryProcessor;
    private EnumSet<Subject> enamSet = EnumSet.noneOf(Subject.class);

    TelegramFacade(QueryProcessor queryProcessor,AlertSender alertSender) {
        this.queryProcessor = queryProcessor;
        this.alertSender = alertSender;
    }

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            if (Subject.contains(update.getCallbackQuery().getData())) {
                Subject element = Subject.valueOf(update.getCallbackQuery().getData());
                return (BotApiMethod<?>) setAndRemoveTick(update, element);
            } else if (update.getCallbackQuery().getData().equals("Delete")) {
                return (BotApiMethod<?>) deleteSelectedSubject(update);
            }
            return null;// Выдать сообщение, я тебя не понимаю
        } else if (update.hasMessage()) {
            queryProcessor.cleanRequests();
            switch (update.getMessage().getText()) {
                case "/subjects":
                    return queryProcessor.processingRequestSubject(update, enamSet);
                case "/specialties":
                    return queryProcessor.processingRequestSpecialties(update);
                case "/help":
                    return queryProcessor.processingRequestHelp(update);
                case "/start":
                    return queryProcessor.processingRequestStart(update);
                case "/contacts":
                    return queryProcessor.processingRequestContacts(update);
                default:
                    //TODO
            }
        }
        return null;
    }

    private EditMessageReplyMarkup deleteSelectedSubject(Update update) {
        enamSet.clear();
        return queryProcessor.processingSelectionSubject(update, enamSet);
    }

    private BotApiMethod<? extends Serializable> setAndRemoveTick(Update update, Subject element) {
        if (enamSet.contains(element)) {
            enamSet.remove(element);
        } else {
            radioButtonImpl(element);
        }
        if (enamSet.size() <= 5) {
            return queryProcessor.processingSelectionSubject(update, enamSet);
        } else {
            return alertSender.sendAlert(update);
        }

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
}