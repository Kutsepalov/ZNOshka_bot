package com.softserve.webhookbot.entity.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MainMenuSender {
    private final ReplyKeyboardMarkup replyKeyboardMarkup;
    private final List<KeyboardRow> rows;
    @Value("${telegrambot.menu-message.chose-subject}")
    private String choseSubject;
    @Value("${telegrambot.menu-message.show-specialties}")
    private String showSpecialties;
    @Value("${telegrambot.menu-message.show-help}")
    private String showHelp;
    @Value("${telegrambot.menu-message.show-contact}")
    private String showContact;


    public ReplyKeyboardMarkup getMenuReply() {
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton(choseSubject));
        row2.add(new KeyboardButton(showSpecialties));
        row3.add(new KeyboardButton(showHelp));
        row4.add(new KeyboardButton(showContact));
        rows.clear();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }
}
