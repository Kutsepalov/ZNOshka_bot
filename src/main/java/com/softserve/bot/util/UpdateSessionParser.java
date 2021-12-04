package com.softserve.bot.util;

import com.softserve.bot.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;
@RequiredArgsConstructor
@Component
public class UpdateSessionParser {
    @Value("${telegrambot.mark.separator}")
    private String separator;
    private String callback;
    private EnumSet<Subject> enumSet;


    public EnumSet<Subject> getEnumSet(Update update) {
        String[] data = update.getCallbackQuery().getData().split(separator);
        enumSet = EnumSetUtil.decode(Integer.parseInt(data[1]), Subject.class);
        return enumSet;
    }

    public String getCallback(Update update) {
        String[] data = update.getCallbackQuery().getData().split(separator);
        callback =data[0];
        return callback;
    }
}
