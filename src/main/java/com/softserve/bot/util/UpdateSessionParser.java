package com.softserve.bot.util;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.model.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@AllArgsConstructor
@Component
public class UpdateSessionParser {
    private BotMessages messages;

    public EnumSet<Subject> getEnumSet(Update update) {
        String[] data = update.getCallbackQuery().getData().split(messages.getSeparator());
        return EnumSetUtil.decode(Integer.parseInt(data[1]), Subject.class);
    }

    public String getCallback(Update update) {
        String[] data = update.getCallbackQuery().getData().split(messages.getSeparator());
        return data[0];
    }
}
