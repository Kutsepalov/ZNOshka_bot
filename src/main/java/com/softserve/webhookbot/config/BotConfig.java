package com.softserve.webhookbot.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotConfig {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.username}")
    private String username;
    @Value("${telegrambot.token}")
    private String token;

    public String getWebHookPath() {
        return webHookPath;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
