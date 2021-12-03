package com.softserve.webhookbot.util;

import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@Component
public class UpdateSessionParser {
    @Value("${telegrambot.mark.separator}")
    private String separator;
    private String callback;
    private EnumSet<Subject> enumSet;


    public void parse(Update update) {
        String[] data = update.getCallbackQuery().getData().split(separator);
        enumSet = EnumSetUtil.decode(Integer.parseInt(data[1]), Subject.class);
        callback =data[0];
    }

    public EnumSet<Subject> getEnumSet() {
        return enumSet;
    }

    public String getCallback() {
        return callback;
    }
}
