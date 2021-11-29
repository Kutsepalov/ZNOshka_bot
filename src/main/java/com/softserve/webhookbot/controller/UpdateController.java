package com.softserve.webhookbot.controller;

import com.softserve.webhookbot.entity.WebhookBot;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
@RestController
public class UpdateController {
    private final WebhookBot telegramBot;

    @PostConstruct
    private void botRegistration() {
        try {
            URL obj = new URL("https://api.telegram.org/bot"
                    + telegramBot.getBotToken()
                    + "/setWebhook?url="
                    + telegramBot.getBotPath());
            log.info("Opening connection upon request \"" + obj + "\"");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                String res = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                if(res.contains("Webhook was set") || res.contains("Webhook is already set")) {
                    log.info("Bot registration success");
                } else {
                    log.error("Bot registration isn't success");
                }
            } else {
                log.error("Response code not OK. Actual: " + responseCode);
            }
        } catch (MalformedURLException e) {
            log.error("URL registration of bot is incorrect format");
        } catch (IOException e) {
            log.error("Failed to open connection to register bot");
        }
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}



