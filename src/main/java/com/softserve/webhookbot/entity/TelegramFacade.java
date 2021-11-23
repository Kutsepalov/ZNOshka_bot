package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.entity.handler.*;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.EnumSet;

@Component
public class TelegramFacade {
    private AlertSender alertSender;
    private AdditionalMessageHandler additionalMessageHandler;
    private ContactsHandler contactsHandler;
    private StartHandler startHandler;
    private HelpHandler helpHandler;
    private SpecializationHandler specializationHandler;
    private SubjectHandler subjectHandler;
    private VersionHendler versionHendler;
    private EnumSet<Subject> enumSet = EnumSet.noneOf(Subject.class);

    TelegramFacade(AlertSender alertSender,
                   AdditionalMessageHandler additionalMessageHandler,
                   ContactsHandler contactsHandler,
                   StartHandler startHandler,
                   HelpHandler helpHandler,
                   SpecializationHandler specializationHandler,
                   SubjectHandler subjectHandler,
                   VersionHendler versionHendler
                   ){
    this.alertSender = alertSender;
    this.additionalMessageHandler = additionalMessageHandler;
    this.contactsHandler = contactsHandler;
    this.startHandler = startHandler;
    this.helpHandler = helpHandler;
    this.specializationHandler = specializationHandler;
    this.subjectHandler = subjectHandler;
    this.versionHendler = versionHendler;
    }

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = versionHendler.parseVersion(update);
            if (versionHendler.getCallbackVersion() != versionHendler.getVersion()) {
                return alertSender.sendVersionAlert(update);
            } else if (Subject.contains(callbackQuery)) {
                Subject element = Subject.valueOf(callbackQuery);
                return setAndRemoveTick(update, element);
            } else if (callbackQuery.equals("Delete")) {
                return deleteSelectedSubject(update);
            } else if (callbackQuery.equals("Search")) {
                if (enumSet.size() >= 3) {
                    //TODO implement filter specialties;
                    return null;
                } else {
                    return alertSender.sendNotEnoughSubject(update);
                }
            }
        } else if (update.hasMessage()) {
            subjectHandler.cleanRequests();
            specializationHandler.cleanRequests();
            startHandler.cleanRequests();
            additionalMessageHandler.cleanRequests();
            switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    versionHendler.updateVersion();
                    enumSet.clear();
                    return subjectHandler.handle(update, enumSet);
                case "Показати всі спеціальності":
                    versionHendler.updateVersion();
                    return specializationHandler.handler(update);
                case "Правила користування":
                    return helpHandler.handler(update);
                case "/start":
                    return startHandler.handler(update);
                case "Наші контакти":
                    return contactsHandler.handler(update);
                default:
                    return additionalMessageHandler.handler(update);
            }
        }
        return null;
    }

    private EditMessageReplyMarkup deleteSelectedSubject(Update update) {
        enumSet.clear();
        return subjectHandler.processingSelectionSubject(update, enumSet);
    }

    private BotApiMethod<? extends Serializable> setAndRemoveTick(Update update, Subject element) {
        if (enumSet.contains(element)) {
            enumSet.remove(element);
            return subjectHandler.processingSelectionSubject(update, enumSet);
        } else if (enumSet.size() < 5) {
            radioButtonImpl(element);
            return subjectHandler.processingSelectionSubject(update, enumSet);
        } else {
            return alertSender.sendSubjectAlert(update);
        }

    }

    private void radioButtonImpl(Subject element) {
        switch (element) {
            case UKRAINIAN:
                enumSet.remove(Subject.LITERATURE);
                enumSet.add(element);
                break;
            case LITERATURE:
                enumSet.remove(Subject.UKRAINIAN);
                enumSet.add(element);
                break;
            case MATHPROFILE:
                enumSet.remove(Subject.MATHSTANDART);
                enumSet.add(element);
                break;
            case MATHSTANDART:
                enumSet.remove(Subject.MATHPROFILE);
                enumSet.add(element);
                break;
            case FRENCH:
                enumSet.remove(Subject.ENGLISH);
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case ENGLISH:
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case GERMANY:
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.ENGLISH);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case SPANISH:
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.ENGLISH);
                enumSet.add(element);
                break;
            default:
                enumSet.add(element);
        }
    }
}