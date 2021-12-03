package com.softserve.bot.util;

import com.softserve.bot.model.Subject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@Component
public class UpdateSessionParser {
    private EnumSet<Subject> enumSet;
    private String callback;


    public void parse(Update update) {
        String[] data = update.getCallbackQuery().getData().split("/");
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
