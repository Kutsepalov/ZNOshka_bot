package com.softserve.webhookbot.config;


import com.softserve.webhookbot.entity.TelegramFacade;
import com.softserve.webhookbot.entity.WebhookBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Configuration
public class AppConfig {
    private final BotConfig botConfig;

    public AppConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public WebhookBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        WebhookBot bot = new WebhookBot(telegramFacade, setWebhook);
        bot.setBotToken(botConfig.getToken());
        bot.setBotUserName(botConfig.getUsername());
        bot.setWebHookPath(botConfig.getWebHookPath());

        return bot;
    }

    @Bean
    EditMessageReplyMarkup editMessageReplyMarkup() {
        return new EditMessageReplyMarkup();
    }

    @Bean
    Message message() {
        return new Message();
    }

    @Bean
    SendMessage sendMessage() {
        return new SendMessage();
    }

    @Bean
    InlineKeyboardMarkup inlineKeyboardMarkup() {
        return new InlineKeyboardMarkup();
    }
    @Bean
    ReplyKeyboardMarkup replyKeyboardMarkup(){
        return new ReplyKeyboardMarkup();
    }
}