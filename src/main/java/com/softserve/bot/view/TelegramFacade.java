package com.softserve.bot.view;

import com.softserve.bot.controller.MailingController;
import com.softserve.bot.model.BotMessages;
import com.softserve.bot.model.entity.User;
import com.softserve.bot.service.database.RequestService;
import com.softserve.bot.service.database.UserService;
import com.softserve.bot.util.EnumSetUtil;
import com.softserve.bot.view.handler.*;
import com.softserve.bot.model.Subject;
import com.softserve.bot.view.sender.AlertSender;
import com.softserve.bot.util.UpdateSessionParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramFacade {
    private final UserService userService;
    private final RequestService requestService;
    private final UndefinedMessageHandler undefinedMessageHandler;
    private final AlertSender alertSender;
    private final SubjectHandler subjectHandler;
    private final SpecializationHandler specializationHandler;
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;
    private final ContactsHandler contactsHandler;
    private final AdditionalMessageHandler additionalMessageHandler;
    private final AdminHandler adminHandler;
    private final UpdateSessionParser updateSessionParser;
    private final BotMessages messages;
    private final MailingController mailing;
    private EnumSet<Subject> enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);

//    Этот коммент стоит удалить
//    Та пусть остается)
//    Пропертя где лежат айди админов
    @Value("${app.bot.admin}")
    private long[] admins;

    public BotApiMethod<?> handleUpdate(Update update) {
        saveUser(update);
        if (update.hasCallbackQuery()) {
            return handleCallback(update);
        } else if (update.getMessage().hasText()) {
            return handleMessage(update);
        }
        return undefinedMessageHandler.handle(update);
    }

    private void saveUser(Update update) {
        Message msg;
        if(update.hasCallbackQuery()) {
            msg = update.getCallbackQuery().getMessage();
        } else {
            msg = update.getMessage();
        }
        userService.save(msg.getChatId());
    }

    private SendMessage handleMessage(Update update) {
        if(Arrays.stream(admins).anyMatch(id -> id == update.getMessage().getChatId())) {
            return handleAdminMessage(update);
        } else {
            return handleUserMessage(update);
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
            return processingSearchCallback(update);
        } else if (callbackQuery.equals(messages.getBranchType())) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchType(update, callback);
        }
        else if (callbackQuery.equals(messages.getBranch())) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleBranchOfKnowledge(update, callback, subjectHandler);
        }
        else if (callbackQuery.equals(messages.getSpecialty())) {
            var callback = updateSessionParser.parseToMap(update);
            return specializationHandler.handleSpeciality(update, callback);
        } else if(callbackQuery.equals("Send")) {
            return adminHandler.handleSend(update);
        }
        return alertSender.undefinedCallback(update);
    }

    private BotApiMethod<?> processingSearchCallback(Update update) {
        if (EnumSetUtil.selectedEnough(enumSet)) {
            if (EnumSetUtil.notOutOfLimit(enumSet)) {
                requestService.save(update.getCallbackQuery().getMessage().getChatId(), EnumSetUtil.code(enumSet));
                enumSet.add(Subject.FOREIGN);
                return specializationHandler.handle(update, enumSet);
            } else {
                return alertSender.sendSubjectAlert(update);
            }
        } else {
            return alertSender.sendNotEnoughSubject(update);
        }
    }


    private SendMessage handleUserMessage(Update update){
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

    private SendMessage handleAdminMessage(Update update) {
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
                if (update.getMessage().getText().startsWith("/send")
                        && Arrays.stream(admins).anyMatch(id -> id == update.getMessage().getChatId())) {
                    List<User> userList = userService.list();
                    mailing.mailingAllUsers(
                            update.getMessage()
                                    .getText()
                                    .replaceFirst("/send", "")
                                    .trim(),
                            userList
                    );
                    return adminHandler.handleText(update);
                }
                else {
                    return additionalMessageHandler.handle(update);
                }
        }
    }

}
