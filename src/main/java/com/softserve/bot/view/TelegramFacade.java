package com.softserve.bot.view;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.util.EnumSetUtil;
import com.softserve.bot.view.handler.*;
import com.softserve.bot.model.Subject;
import com.softserve.bot.view.sender.AlertSender;
import com.softserve.bot.util.UpdateSessionParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class TelegramFacade {
    @Value("845514423")
    private String ADMIN;
    private final UndefinedMessageHandler undefinedMessageHandler;
    private final AlertSender alertSender;
    private final SubjectHandler subjectHandler;
    private final SpecializationHandler specializationHandler;
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;
    private final ContactsHandler contactsHandler;
    private final AdditionalMessageHandler additionalMessageHandler;
    private final UpdateSessionParser updateSessionParser;
    private final AdminHandler adminHandler;
    private final BotMessages messages;
    private EnumSet<Subject> enumSet = EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE);

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return handleCallback(update);
        } else if (update.getMessage().hasText()) {
            return handleMessage(update);
        }
        return undefinedMessageHandler.handle(update);
    }

    private SendMessage handleMessage(Update update) {
        if((String.valueOf(update.getMessage().getChatId()).equals(ADMIN))) {
            switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    enumSet = EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE);
                    return subjectHandler.handle(update);
                case "Показати всі спеціальності":
                    return specializationHandler.handle(update);
                case "Правила користування":
                    return helpHandler.handle(update);
                case "/start":
                    return startHandler.handleAdmin(update);
                case "Наші контакти":
                    return contactsHandler.handle(update);
                case "Адмін панель:":
                    return adminHandler.handle(update);
                default:
                    return adminHandler.handleText(update);
                }
            } else {
                switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    enumSet = EnumSet.of(Subject.LITERATURE, Subject.MATH_PROFILE);
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
            enumSet = updateSessionParser.getEnumSet(update);
            if (EnumSetUtil.selectedEnough(enumSet)) {
                if (EnumSetUtil.notOutOfLimit(enumSet)) {
                    enumSet.add(Subject.FOREIGN);
                    return specializationHandler.handle(update, enumSet);
                } else {
                    return alertSender.sendSubjectAlert(update);
                }
            } else {
                return alertSender.sendNotEnoughSubject(update);
            }
        } else if (callbackQuery.equals("Branch type")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchType(update,callback);
        }
        else if (callbackQuery.equals("Branch")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchOfKnowledge(update,callback,subjectHandler);
        }
        else if (callbackQuery.equals("Speciality")) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleSpeciality(update,callback);
        } else if(callbackQuery.equals("Send")) {
            var callback = updateSessionParser.parseToMap(update);
            return adminHandler.handleSend(update, callback);
        }
        return alertSender.undefinedCallback(update);
    }
}
