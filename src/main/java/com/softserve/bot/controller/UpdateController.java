package com.softserve.bot.controller;

import com.softserve.bot.model.Subject;
import com.softserve.bot.util.EnumSetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@AllArgsConstructor
@RestController()
@RequestMapping("/")
public class UpdateController {
    private final WebhookBot telegramBot;

    /**
     *  Method is called after bean initialization.
     *  Functional creates request upon link which register webhook link on Telegram server.
     *  Webhook link - link to which requests for this application are sent.
     */
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
            if (responseCode == HttpURLConnection.HTTP_OK) {
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

    /**
     * Method for checking life state this app.
     *
     * @return empty response with 204 code.
     */
    @GetMapping
    public ResponseEntity<Void> root() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method for getting messages from Telegram users.
     *
     * @param update model with user message values.
     * @return messages for users or NULL if Update is empty.
     */
    @PostMapping
    public Validable onUpdateReceived(@RequestBody Update update) {
        if(!update.hasMessage() && !update.hasCallbackQuery()) {
            return null;
        }
        logger(update);
        return telegramBot.onWebhookUpdateReceived(update);
    }

    private void logger(Update update) {
        Message msg;
        if(update.hasMessage()) {
            msg = update.getMessage();
            log.info(chatData(msg) + " - " + (msg.getText()==null ? "<empty>" : ("\"" + msg.getText() + "\"")));
        } else if(update.hasCallbackQuery()) {
            CallbackQuery cbq = update.getCallbackQuery();
            msg = cbq.getMessage();
            String[] data = update.getCallbackQuery().getData().split("/");
            if (data[0].equals("Search")) {
                log.info(chatData(msg) + " - SEARCH" + EnumSetUtil.decode(Integer.parseInt(data[1]), Subject.class));
            }
        }
    }

    private String chatData(Message msg) {
        return "Chat[" + msg.getChatId() + "]:"
                + (msg.getChat().getUserName()==null ? "<hidden>" : msg.getChat().getUserName());
    }
}