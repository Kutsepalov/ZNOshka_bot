package com.softserve.webhookbot.entity;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class VersionHendler {
    private long callbackVersion=0;
    private long version=0;

    public long getVersion() {
        return version;
    }

    void updateVersion() {
        version++;
    }

    String parseVersion(Update update) {
    String[] data = update.getCallbackQuery().getData().split("/");
    callbackVersion = Long.parseLong(data[1]);
    return data[0];
    }

    long getCallbackVersion() {
        return callbackVersion;
    }
}
