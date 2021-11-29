package com.softserve.webhookbot.entity;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;


public class WebhookBot extends SpringWebhookBot {
    private String webhookLink;
    private String botUsername;
    private String botToken;
    private final TelegramFacade telegramFacade;

    public WebhookBot(TelegramFacade telegramFacade, DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
        this.telegramFacade = telegramFacade;
    }
    public WebhookBot(TelegramFacade telegramFacade, SetWebhook setWebhook) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return webhookLink;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setWebHookPath(String webHookPath) {
        this.webhookLink = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUsername = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
