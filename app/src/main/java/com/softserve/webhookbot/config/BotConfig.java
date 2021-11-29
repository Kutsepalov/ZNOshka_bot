package com.softserve.webhookbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotConfig {
    @Value("#{environment.APP_WEBHOOK_LINK}")
    private String webhookLink;
    @Value("#{environment.APP_BOT_USERNAME}")
    private String username;
    @Value("#{environment.APP_BOT_TOKEN}")
    private String token;

    public String getWebhookLink() {
        return webhookLink;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

}
