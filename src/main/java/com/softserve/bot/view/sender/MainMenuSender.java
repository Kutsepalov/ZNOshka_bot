package com.softserve.bot.view.sender;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@AllArgsConstructor
@Component
public class MainMenuSender {
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private List<KeyboardRow> rows;


     public ReplyKeyboardMarkup getMenuReply() {
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton("Вибрати предмети"));
        row2.add(new KeyboardButton("Показати всі спеціальності"));
        row3.add(new KeyboardButton("Правила користування"));
        row4.add(new KeyboardButton("Наші контакти"));
        rows.clear();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }
}
