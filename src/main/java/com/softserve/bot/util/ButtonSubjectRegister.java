package com.softserve.bot.util;

import com.softserve.bot.model.Subject;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ButtonSubjectRegister {
    @Value("${telegrambot.text.find-specialty}")
    private String findSpecialty;
    @Value("${telegrambot.text.delete-all}")
    private  String deleteAll;
    @Value("${telegrambot.text.out-of-limit}")
    private  String outOfLimit;
    @Value("${telegrambot.text.to-main-menu}")
    private String toMainMenu;
    @Value("${telegrambot.mark.tick}")
    private  String tick;
    @Value("${telegrambot.mark.find}")
    private  String mag;
    @Value("${telegrambot.mark.out-of-limit}")
    private  String exclamationMark;
    @Value("${telegrambot.mark.separator}")
    private String separator;
    @Value("${telegrambot.data.search}")
    private String searchData;
    @Value("${telegrambot.data.delete}")
    private String deleteData;
    private static final int MAX_SIZE = 5;


    private final List<List<InlineKeyboardButton>> rowList;
    private final List<List<InlineKeyboardButton>> additionalSubjectRow;
    private final List<InlineKeyboardButton> ukraineRow;
    private final List<InlineKeyboardButton> mathRow;
    private final List<InlineKeyboardButton> languageRow;
    private final List<InlineKeyboardButton> deleteRow;
    private final List<InlineKeyboardButton> findRow;
    private final List<InlineKeyboardButton> menuRow;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;

    private void clearAllRow() {
        menuRow.clear();
        additionalSubjectRow.clear();
        rowList.clear();
        ukraineRow.clear();
        mathRow.clear();
        languageRow.clear();
        deleteRow.clear();
        findRow.clear();
    }


    public InlineKeyboardMarkup getInlineSubjectButtons(Set enumSet, int counter) {
        clearAllRow();
        addDeleteButton(counter, enumSet);
        for (Subject subject : Subject.values()) {
            List<InlineKeyboardButton> singleButtonRow = new ArrayList<>();
            InlineKeyboardButton currentButton = new InlineKeyboardButton();
            String text = subject.getName();
            String data = subject.name();
            subjectChoice(singleButtonRow, currentButton, enumSet, text, data);
        }
        rowList.add(ukraineRow);
        rowList.add(mathRow);
        rowList.addAll(additionalSubjectRow);
        rowList.add(languageRow);
        addFindButton(enumSet);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private void addFindButton(Set enumSet) {
        InlineKeyboardButton currentButton = new InlineKeyboardButton();
        currentButton.setText(findSpecialty + " " + EmojiParser.parseToUnicode(mag));
        currentButton.setCallbackData(searchData + separator + EnumSetUtil.code((EnumSet<Subject>) enumSet));
        findRow.add(currentButton);
        rowList.add(findRow);
    }

    private void addDeleteButton(int counter, Set enumSet) {
        InlineKeyboardButton currentButton = new InlineKeyboardButton();
        if (enumSet.contains(Subject.CREATIVE_COMPETITION)) {
            counter--;
        }
        if (EnumSetUtil.notOutOfLimit(enumSet)) {
            currentButton.setText(deleteAll + " "
                    + counter + separator
                    + " " + MAX_SIZE
                    + EmojiParser.parseToUnicode(tick));
        } else {
            currentButton.setText(deleteAll + " "
                    + counter + separator + " "
                    + MAX_SIZE + EmojiParser.parseToUnicode(tick)
                    + EmojiParser.parseToUnicode(exclamationMark)
                    + " " + outOfLimit);
        }
        currentButton.setCallbackData(deleteData + separator + EnumSetUtil.code((EnumSet<Subject>)enumSet));
        deleteRow.add(currentButton);
        rowList.add(deleteRow);
    }

    private void subjectChoice(List<InlineKeyboardButton> keyboardButtonsRow,
                               InlineKeyboardButton subjectButton,
                               Set enumSet,
                               String text,
                               String data) {
        switch (text) {
            case "Українська мова":
            case "Українська мова і література":
                setTextAndData(subjectButton, enumSet, text, data);
                ukraineRow.add(subjectButton);
                break;
            case "Математика (стандартна)":
            case "Математика (профільна)":
                setTextAndData(subjectButton, enumSet, text, data);
                mathRow.add(subjectButton);
                break;
            case "Англійська мова":
            case "Французька мова":
            case "Іспанська мова":
            case "Німецька мова":
                setTextAndData(subjectButton, enumSet, text, data);
                languageRow.add(subjectButton);
                break;
            default:
                setTextAndData(subjectButton, enumSet, text, data);
                keyboardButtonsRow.add(subjectButton);
                additionalSubjectRow.add(keyboardButtonsRow);
        }
    }

    private void setTextAndData(InlineKeyboardButton subjectButton, Set enumSet, String text, String data) {
        if (enumSet.contains(Subject.valueOf(data))) {
            subjectButton.setText(EmojiParser.parseToUnicode(tick) + text);
        } else {
            subjectButton.setText(text);
        }
        subjectButton.setCallbackData(data + separator + EnumSetUtil.code((EnumSet<Subject>) enumSet));
    }

}
