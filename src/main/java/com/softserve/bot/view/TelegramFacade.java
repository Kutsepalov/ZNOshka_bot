package com.softserve.bot.view;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.util.EnumSetUtil;
import com.softserve.bot.view.handler.*;
import com.softserve.bot.model.Subject;
import com.softserve.bot.view.sender.AlertSender;
import com.softserve.bot.util.UpdateSessionParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class TelegramFacade {
    private final UndefinedMessageHandler undefinedMessageHandler;
    private final AlertSender alertSender;
    private final SubjectHandler subjectHandler;
    private final SpecializationHandler specializationHandler;
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;
    private final ContactsHandler contactsHandler;
    private final AdditionalMessageHandler additionalMessageHandler;
    private final UpdateSessionParser updateSessionParser;
    private final BotMessages messages;
    private EnumSet<Subject> enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallback(update);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            return handleMessage(update);
        }
        return undefinedMessageHandler.handle(update);
    }

    private SendMessage handleMessage(Update update) {
        switch (update.getMessage().getText()) {
            case "Вибрати предмети":
                enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);
                return subjectHandler.handle(update);
            case "Показати всі спеціальності":
                return specializationHandler.handle(update);
            case "Правила користування":
                return helpHandler.handle(update);
            case "/start":
                return startHandler.handle(update);
            case "Наші контакти":
                return contactsHandler.handle(update);
            default:
                return additionalMessageHandler.handle(update);
        }
    }

    private BotApiMethod<?> handleCallback(Update update) {
        String callbackQuery = updateSessionParser.getCallback(update);
        if (Subject.contains(callbackQuery)) {
            Subject element = Subject.valueOf(callbackQuery);
            enumSet = updateSessionParser.getEnumSet(update);
            return subjectHandler.setAndRemoveTick(update, element, enumSet);
        } else if (callbackQuery.equals(messages.getDeleteData())) {
            enumSet = updateSessionParser.getEnumSet(update);
            return subjectHandler.deleteSelectedSubject(update, enumSet);
        } else if (callbackQuery.equals(messages.getSearchData())) {
            return processingSearchCallback(update);
        } else if (callbackQuery.equals("Branch type")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchType(update, callback.get("text"));
        } else if (callbackQuery.equals("Branch")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchOfKnowledge(update, callback, subjectHandler);
        } else if (callbackQuery.equals("Speciality")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleSpeciality(update, callback);
        }
        return alertSender.undefinedCallback(update);
    }

    private BotApiMethod<?> processingSearchCallback(Update update) {
        enumSet = updateSessionParser.getEnumSet(update);
        if (EnumSetUtil.selectedEnough(enumSet)) {
            if (EnumSetUtil.notOutOfLimit(enumSet)) {
                return processingSpecialty(update);
            } else {
                return alertSender.sendSubjectAlert(update);
            }
        } else {
            return alertSender.sendNotEnoughSubject(update);
        }
    }

    private BotApiMethod<?> processingSpecialty(Update update) {
        if (EnumSetUtil.containsForeignLanguage(enumSet)) {
            enumSet.add(Subject.FOREIGN);
        }
        return specializationHandler.handle(update, enumSet);
    }
}
