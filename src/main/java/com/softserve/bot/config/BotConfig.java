package com.softserve.bot.config;

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

    String getWebhookLink() {
        return webhookLink;
    }

    String getUsername() {
        return username;
    }

    String getToken() {
        return token;
    }

}
