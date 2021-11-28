package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.entity.handler.*;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.util.EnumSetUtil;
import com.softserve.webhookbot.enumeration.Subject;
import com.softserve.webhookbot.util.RadioButton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;
@RequiredArgsConstructor
@Component
public class TelegramFacade {
    private final RadioButton radioButton;
    private final AlertSender alertSender;
    private final SubjectHandler subjectHandler;
    private final SpecializationHandler specializationHandler;
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;
    private final ContactsHandler contactsHandler;
    private final AdditionalMessageHandler additionalMessageHandler;
    private final VersionChanger versionChanger;
    private EnumSet<Subject> enumSet = EnumSet.of(Subject.UKRAINIAN,Subject.MATH_PROFILE);

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            versionChanger.parseVersion(update);
            String callbackQuery = versionChanger.getCallback();
            enumSet = versionChanger.getEnumSet();
            if (versionChanger.getCallbackVersion() != versionChanger.getVersion()) {
                return alertSender.sendVersionAlert(update);
            } else if (Subject.contains(callbackQuery)) {
                Subject element = Subject.valueOf(callbackQuery);
                return subjectHandler.setAndRemoveTick(update, element, enumSet);
            } else if (callbackQuery.equals("Delete")) {
                return subjectHandler.deleteSelectedSubject(update, enumSet);
            } else if (callbackQuery.equals("Search")) {
                if (RadioButton.notEnough(enumSet)) {
                    return specializationHandler.handleFiltered(update, enumSet);
                } else {
                    return alertSender.sendNotEnoughSubject(update);
                }
            }
        } else if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    versionChanger.updateVersion();
                    enumSet = EnumSet.of(Subject.UKRAINIAN,Subject.MATH_PROFILE);
                    return subjectHandler.handle(update, enumSet);
                case "Показати всі спеціальності":
                    versionChanger.updateVersion();
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

}
