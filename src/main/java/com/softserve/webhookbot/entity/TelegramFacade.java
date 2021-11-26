package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.entity.handler.*;
import com.softserve.webhookbot.entity.sender.AlertSender;
import com.softserve.webhookbot.enumeration.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;
@RequiredArgsConstructor
@Component
public class TelegramFacade {
    private final VersionChanger versionChanger;
    private final ApplicationContext context;
    private EnumSet<Subject> enumSet = EnumSet.noneOf(Subject.class);

    BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            versionChanger.parseVersion(update);
            String callbackQuery = versionChanger.getCallback();
            enumSet = versionChanger.getEnumSet();
            if (versionChanger.getCallbackVersion() != versionChanger.getVersion()) {
                return context.getBean(AlertSender.class).sendVersionAlert(update);
            } else if (Subject.contains(callbackQuery)) {
                Subject element = Subject.valueOf(callbackQuery);
                return context.getBean(SubjectHandler.class).setAndRemoveTick(update, element, enumSet);
            } else if (callbackQuery.equals("Delete")) {
                return context.getBean(SubjectHandler.class).deleteSelectedSubject(update, enumSet);
            } else if (callbackQuery.equals("Search")) {
                if (enumSet.size() >= 3) {
                    return context.getBean(SpecializationHandler.class).handleFiltered(update, enumSet);
                } else {
                    return context.getBean(AlertSender.class).sendNotEnoughSubject(update);
                }
            }
        } else if (update.hasMessage()) {
            switch (update.getMessage().getText()) {
                case "Вибрати предмети":
                    versionChanger.updateVersion();
                    enumSet.clear();
                    return context.getBean(SubjectHandler.class).handle(update, enumSet);
                case "Показати всі спеціальності":
                    versionChanger.updateVersion();
                    return context.getBean(SpecializationHandler.class).handler(update);
                case "Правила користування":
                    return context.getBean(HelpHandler.class).handler(update);
                case "/start":
                    return context.getBean(StartHandler.class).handler(update);
                case "Наші контакти":
                    return context.getBean(ContactsHandler.class).handler(update);
                default:
                    return context.getBean(AdditionalMessageHandler.class).handler(update);
            }
        }
        return null;
    }

}
