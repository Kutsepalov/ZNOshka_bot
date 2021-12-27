package com.softserve.bot;

import com.softserve.bot.service.parser.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class WebhookBotStarter {

    public static void main(String[] args) {
        SpringApplication.run(WebhookBotStarter.class, args);
    }
    @Bean
    public CommandLineRunner commands(Parser parser) {
        return (String[] args) -> {
            if(Arrays.stream(args).anyMatch(arg -> "-u".equals(arg) || "-update".equals(arg))) {
                parser.doParseExcelToCSV();
                log.info("CSV file was updated");
            }
        };
    }
}
