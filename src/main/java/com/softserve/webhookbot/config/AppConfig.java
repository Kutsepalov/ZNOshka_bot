package com.softserve.webhookbot.config;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.softserve.webhookbot.entity.BotMessages;
import com.softserve.webhookbot.entity.TelegramFacade;
import com.softserve.webhookbot.entity.WebhookBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
@Configuration
public class AppConfig {
    private final BotConfig botConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebhookLink()).build();
    }

    @Bean
    public WebhookBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        WebhookBot bot = new WebhookBot(telegramFacade, setWebhook);
        bot.setBotToken(botConfig.getToken());
        bot.setBotUserName(botConfig.getUsername());
        bot.setWebHookPath(botConfig.getWebhookLink());

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

    @Bean
    BotMessages botMessages() {
        XmlMapper xmlMapper = new XmlMapper();
        String xml;
        BotMessages instance = null;
        try {
            xml = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("bot-msg.xml"), StandardCharsets.UTF_8);
            instance = xmlMapper.readValue(xml, BotMessages.class);
        } catch (SecurityException e) {
            log.error("File \"bot-msg.xml\" read access denied");
        } catch (FileNotFoundException e) {
            log.error("File \"bot-msg.xml\" not found");
        } catch (JacksonException e) {
            log.error("Entry of file \"bot-msg.xml\" incorrect");
        } catch (IOException e) {
            log.error("Error reading file: " + e.getMessage());
        }
        return instance;
    }
}