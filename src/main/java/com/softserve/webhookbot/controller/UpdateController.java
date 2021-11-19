package com.softserve.webhookbot.controller;

import com.softserve.webhookbot.entity.WebhookBot;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
@RestController
public class UpdateController {
    private WebhookBot telegramBot;

    public UpdateController(WebhookBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}



