package com.softserve.bot.view.handler;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Component
public class SpecializationHandler implements Handler {
    private Map<String, List<Specialty>> filteredSpecialty;
    private Message message;
    private SendMessage sendMessage;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        // прикрепть кнопки выбора специальности
        sendMessage.setText("Cпеціальності:from menu");
        return sendMessage;
    }

    public SendMessage handle(Update update, Set<Subject> enumSet) {
        cleanRequests();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Cпеціальності:from search");
        return sendMessage;
    }

}
