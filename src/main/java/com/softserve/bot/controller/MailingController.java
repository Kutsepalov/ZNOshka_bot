package com.softserve.bot.controller;

import com.softserve.bot.config.BotConfig;
import com.softserve.bot.model.BotMessages;
import com.softserve.bot.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@AllArgsConstructor
@Component
public class MailingController {
    private final BotConfig botConfig;
    private final BotMessages botMessages;

    private void mailingUser(String text, String chatId, AtomicInteger amount) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl("https://api.telegram.org"
                        +botMessages.getSeparator()
                        +"bot"
                        + botConfig.getToken()
                        + botMessages.getSeparator()
                        +"sendMessage")
                .queryParam("chat_id",chatId)
                .queryParam("text",text);
        try {
            restTemplate.getForEntity(builder.build().encode().toUri(),String.class);
            log.debug("User[" + chatId + "] got message");
            amount.incrementAndGet();
        } catch (HttpClientErrorException ex) {
            log.debug("User[" + chatId + "] blocked bot");
        }
    }

    public void mailingAllUsers(String text, List<User> users) {
        AtomicInteger goodSending = new AtomicInteger();
        ExecutorService ex = Executors.newFixedThreadPool(10);
        users.stream()
                .map(User::getId)
                .forEach(user -> ex.submit(()->mailingUser(text,String.valueOf(user), goodSending)));
        ex.shutdown();
        try {
            if(!ex.awaitTermination(120, TimeUnit.SECONDS)) {
                log.warn("Mailing has interrupted. Thread lifetime is 120 seconds.");
            } else {
                log.info(goodSending.get() + " out of " + users.size() + " users received messages");
            }
        } catch (InterruptedException e) {
            log.debug("Thread[" + Thread.currentThread().getName() + "] was interrupted. " + e.getMessage());
        }
    }
}