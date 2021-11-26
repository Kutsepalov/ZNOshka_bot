package com.softserve.webhookbot.config;

import com.softserve.webhookbot.entity.VersionChanger;
import com.softserve.webhookbot.enumeration.EnumSetUtil;
import com.softserve.webhookbot.enumeration.Subject;
import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@AllArgsConstructor
@Component
public class ButtonSubjectRegister {
    private EnumSetUtil enumSetUtil;
    private List<List<InlineKeyboardButton>> rowList;
    private List<InlineKeyboardButton> ukraineRow;
    private List<InlineKeyboardButton> mathRow;
    private List<InlineKeyboardButton> languageRow;
    private List<InlineKeyboardButton> deleteRow;
    private List<InlineKeyboardButton> findRow;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private VersionChanger versionChanger;

    private void clearAllRow() {
        rowList.clear();
        ukraineRow.clear();
        mathRow.clear();
        languageRow.clear();
        deleteRow.clear();
        findRow.clear();
    }



    public InlineKeyboardMarkup getInlineSubjectButtons(EnumSet<Subject> enumSet, int counter) {
        clearAllRow();
        addDeleteButton(counter,enumSet);
        for (Subject subject : Subject.values()) {
            List<InlineKeyboardButton> singleButtonRow = new ArrayList<>();
            InlineKeyboardButton currentButton = new InlineKeyboardButton();
            String text = subject.getName();
            String data = subject.name();
            subjectChoice(singleButtonRow, currentButton, enumSet, text, data);
        }
        rowList.add(ukraineRow);
        rowList.add(mathRow);
        rowList.add(languageRow);
        addFindButton(enumSet);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private void addFindButton(EnumSet<Subject> enumSet) {
        InlineKeyboardButton currentButton = new InlineKeyboardButton();
        currentButton.setText("Знайти спеціальності"+" "+EmojiParser.parseToUnicode(":mag:"));
        currentButton.setCallbackData("Search"+"/"+ versionChanger.getVersion()+"/"+EnumSetUtil.code(enumSet));
        findRow.add(currentButton);
        rowList.add(findRow);
    }

    private void addDeleteButton(int counter,EnumSet<Subject> enumSet) {
        InlineKeyboardButton currentButton = new InlineKeyboardButton();
        currentButton.setText("Видалити всі"+" "+counter+"/"+" "+"5"+EmojiParser.parseToUnicode(":white_check_mark:"));
        currentButton.setCallbackData("Delete"+"/"+ versionChanger.getVersion()+"/"+EnumSetUtil.code(enumSet));
        deleteRow.add(currentButton);
        rowList.add(deleteRow);
    }

    private void subjectChoice(List<InlineKeyboardButton> keyboardButtonsRow,
                               InlineKeyboardButton subjectButton,
                               EnumSet<Subject> enumSet,
                               String text,
                               String data) {
        switch (text) {
            case "Українська мова":
            case "Українська мова і література":
                setTextAndData(subjectButton, enumSet, text, data);
                ukraineRow.add(subjectButton);
                break;
            case "Математика (рівня стандарту)":
            case "Математика (рівня профільний)":
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
                rowList.add(keyboardButtonsRow);
        }
    }

    private void setTextAndData(InlineKeyboardButton subjectButton, EnumSet<Subject> enumSet, String text, String data) {
        if (enumSet.contains(Subject.valueOf(data))) {
            subjectButton.setText(EmojiParser.parseToUnicode(":white_check_mark:") + text);
        } else {
            subjectButton.setText(text);
        }
        int f = EnumSetUtil.code(enumSet);
        subjectButton.setCallbackData(data+"/"+ versionChanger.getVersion()+"/"+EnumSetUtil.code(enumSet));
    }

}
