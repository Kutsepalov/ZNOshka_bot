package com.softserve.webhookbot.config;

import com.softserve.webhookbot.enumeration.Subject;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Component
public class ButtonRegister {
    private List<List<InlineKeyboardButton>> rowList;
    private List<InlineKeyboardButton> ukraineRow;
    private List<InlineKeyboardButton> mathRow;
    private List<InlineKeyboardButton> languageRow;
    private InlineKeyboardMarkup inlineKeyboardMarkup;

    public InlineKeyboardMarkup getInlineSubjectButtons(EnumSet<Subject> enumSet) {
        initInlineMethodTools();
        for (Subject subject : Subject.values()) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            InlineKeyboardButton subjectButton = new InlineKeyboardButton();
            String text = subject.getName();
            String data = subject.name();
            subjectChoice(rowList, ukraineRow, mathRow, languageRow, keyboardButtonsRow, subjectButton, enumSet, text, data);
        }
        rowList.add(ukraineRow);
        rowList.add(mathRow);
        rowList.add(languageRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private void initInlineMethodTools() {
        rowList = new ArrayList<>();
        ukraineRow = new ArrayList<>();
        mathRow = new ArrayList<>();
        languageRow = new ArrayList<>();
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
    }

    private void subjectChoice(List<List<InlineKeyboardButton>> rowList,
                               List<InlineKeyboardButton> ukraineRow,
                               List<InlineKeyboardButton> mathRow,
                               List<InlineKeyboardButton> languageRow,
                               List<InlineKeyboardButton> keyboardButtonsRow,
                               InlineKeyboardButton subjectButton,
                               EnumSet<Subject> enumSet,
                               String text,
                               String data) {
        switch (text) {
            case "Українська мова":
            case "Українська мова і література":
                setButtonParameters(rowList, ukraineRow, keyboardButtonsRow, subjectButton, enumSet, text, data);
                break;
            case "Математика (рівня стандарту)":
            case "Математика (рівня профільний)":
                setButtonParameters(rowList, mathRow, keyboardButtonsRow, subjectButton, enumSet, text, data);
                break;
            case "Англійська мова":
            case "Французька мова":
            case "Іспанська мова":
            case "Німецька мова":
                setButtonParameters(rowList, languageRow, keyboardButtonsRow, subjectButton, enumSet, text, data);
                break;
            default:
                setButtonParameters(rowList, keyboardButtonsRow, keyboardButtonsRow, subjectButton, enumSet, text, data);
        }
    }

    private void setButtonParameters(List<List<InlineKeyboardButton>> rowList,
                                     List<InlineKeyboardButton> ukraineRow,
                                     List<InlineKeyboardButton> keyboardButtonsRow,
                                     InlineKeyboardButton subjectButton,
                                     EnumSet<Subject> enumSet,
                                     String text,
                                     String data) {
        if (enumSet.contains(Subject.valueOf(data))) {
            subjectButton.setText(EmojiParser.parseToUnicode(":white_check_mark:") + text);
        } else {
            subjectButton.setText(text);
        }
        subjectButton.setCallbackData(data);
        ukraineRow.add(subjectButton);
        rowList.add(keyboardButtonsRow);
    }

}
