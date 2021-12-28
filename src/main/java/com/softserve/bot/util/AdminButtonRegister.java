package com.softserve.bot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class AdminButtonRegister {

    private static final String SEND = "Send";
    private static final String CONFIRMED = "Confirmed";
    private static final String SEND_TEXT = "Розіслати повідомлення усім користувачам";
    private static final String SEND_CALLBACK = "Розіслати";
    private static final String CONFIRMED_SEND_TEXT = "Відправити всім";
    private static final String CONFIRMED_BACK_TEXT = "Відмінити";

    public static InlineKeyboardMarkup getAdminKeyboard(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(getSingleButtonRow(SEND_TEXT,buildCallback(SEND ,SEND_CALLBACK)));

        keyboard.setKeyboard(rows);
        return keyboard;
    }

    public static InlineKeyboardMarkup getConfirmationKeyboard(){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(getSingleButtonRow(CONFIRMED_SEND_TEXT, buildCallback(CONFIRMED, CONFIRMED_SEND_TEXT)));
        rows.add(getSingleButtonRow(CONFIRMED_BACK_TEXT, buildCallback(CONFIRMED, CONFIRMED_BACK_TEXT)));

        keyboard.setKeyboard(rows);
        return keyboard;
    }

    protected static List<InlineKeyboardButton> getSingleButtonRow(String text, String callback){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    protected static String buildCallback(String ... callback){
        return String.join("/",callback);
    }

}
