package com.softserve.bot.util;

import com.softserve.bot.model.BotMessages;
import com.softserve.bot.model.Subject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String,String> parseToMap(Update update){
        Map<String, String> map = new HashMap<>();
        String[] data = update.getCallbackQuery().getData().split("/");
        map.put("type",data[0]);
        map.put("text",data[1]);
        if(data.length > 2)
            map.put("branch type",data[2]);

        return map;
    }
}
