package com.softserve.webhookbot.entity;

import com.softserve.webhookbot.util.EnumSetUtil;
import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumSet;

@Component
public class VersionChanger {
    private EnumSet<Subject> enumSet;
    private String callback;
    private long callbackVersion = 0;
    private long version = 0;

    public long getVersion() {
        return version;
    }

    void updateVersion() {
        version++;
    }

    void parseVersion(Update update) {
        String[] data = update.getCallbackQuery().getData().split("/");
        callbackVersion = Long.parseLong(data[1]);
        enumSet = EnumSetUtil.decode(Integer.parseInt(data[2]), Subject.class);
        callback =data[0];
    }

    EnumSet<Subject> getEnumSet() {
        return enumSet;
    }

    String getCallback() {
        return callback;
    }

    long getCallbackVersion() {
        return callbackVersion;
    }
}
