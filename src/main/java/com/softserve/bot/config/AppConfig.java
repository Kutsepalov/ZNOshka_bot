package com.softserve.bot.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.softserve.bot.model.BotMessages;
import com.softserve.bot.view.TelegramFacade;
import com.softserve.bot.controller.WebhookBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    EditMessageText editMessageText(){return new EditMessageText();}

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(
            @Value("#{environment.APP_BOT_DATABASE ?: 'znoshka-bot.db'}")
            String dbPath,
            DataSourceProperties properties
    ) {
        return DataSourceBuilder.create(properties.getClassLoader())
                .driverClassName("org.sqlite.JDBC")
                .url("jdbc:sqlite:" + dbPath)
                .build();
    }

    @Bean
    BotMessages botMessages() {
        XmlMapper xmlMapper = new XmlMapper();
        String xml;
        BotMessages instance = null;
        try {
            xml = IOUtils.toString(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bot-msg.xml")), StandardCharsets.UTF_8);
            instance = xmlMapper.readValue(xml, BotMessages.class);
        } catch (SecurityException e) {
            log.error("File \"bot-msg.xml\" read access denied");
            System.exit(1);
        } catch (FileNotFoundException e) {
            log.error("File \"bot-msg.xml\" not found");
            System.exit(1);
        } catch (JacksonException e) {
            log.error("Entry of file \"bot-msg.xml\" incorrect");
            System.exit(1);
        } catch (IOException | NullPointerException e) {
            log.error("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        return instance;
    }
}