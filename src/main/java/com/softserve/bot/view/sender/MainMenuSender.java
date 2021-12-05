package com.softserve.bot.view.sender;

import com.softserve.bot.model.BotMessages;
import lombok.RequiredArgsConstructor;
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

    private final BotMessages messages;

    public ReplyKeyboardMarkup getMenuReply() {
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton(messages.getChooseSubjectMenu()));
        row2.add(new KeyboardButton(messages.getShowSpecialtiesMenu()));
        row3.add(new KeyboardButton(messages.getRulesMenu()));
        row4.add(new KeyboardButton(messages.getContactsMenu()));
        rows.clear();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }
}
