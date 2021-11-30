package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.entity.handler.*;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.enumeration.Subject;
import com.softserve.webhookbot.util.RadioButton;
import com.softserve.webhookbot.util.UpdateSessionParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class TelegramFacade {
    private final AlertSender alertSender;
    private final SubjectHandler subjectHandler;
    private final SpecializationHandler specializationHandler;
    private final HelpHandler helpHandler;
    private final StartHandler startHandler;
    private final ContactsHandler contactsHandler;
    private final AdditionalMessageHandler additionalMessageHandler;
    private final UpdateSessionParser updateSessionParser;
    private EnumSet<Subject> enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            updateSessionParser.parse(update);
            String callbackQuery = updateSessionParser.getCallback();
            enumSet = updateSessionParser.getEnumSet();
            if (Subject.contains(callbackQuery)) {
                Subject element = Subject.valueOf(callbackQuery);
                return subjectHandler.setAndRemoveTick(update, element, enumSet);
            } else if (callbackQuery.equals("Delete")) {
                return subjectHandler.deleteSelectedSubject(update, enumSet);
            } else if (callbackQuery.equals("Search")) {
                if (RadioButton.selectedEnough(enumSet)) {
                    if (RadioButton.notOutOfLimit(enumSet)) {
                        return specializationHandler.handle(update, enumSet);
                    } else {
                        return alertSender.sendSubjectAlert(update);
                    }
                } else {
                    return alertSender.sendNotEnoughSubject(update);
                }
            }
        } else if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    enumSet = EnumSet.of(Subject.UKRAINIAN, Subject.MATH_PROFILE);
                    return subjectHandler.handle(update, enumSet);
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
        return null;
    }

}
