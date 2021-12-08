package com.softserve.bot.view.handler;

import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.service.Filter;
import com.softserve.bot.util.EnumSetUtil;
import com.softserve.bot.util.SpecialityButtonRegister;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Component
public class SpecializationHandler implements Handler {
    private Message message;
    private SendMessage sendMessage;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }


    public SendMessage handle(Update update, EnumSet<Subject> enumSet) {
        message = update.getCallbackQuery().getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        var branches = Filter.getFiltered(enumSet);
        var branchesOfKnowledge = Filter.getBranchesByName(branches);
        sendMessage.setReplyMarkup(SpecialityButtonRegister.getBranchOfKnowledgeKeyboard(branchesOfKnowledge,enumSet));
        sendMessage.setText("Галузі по вибраним предметам: ");
        return sendMessage;
    }

    public SendMessage handle(Update update) {
        cleanRequests();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        if(update.getCallbackQuery() != null) {
            sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            sendMessage.setReplyMarkup(SpecialityButtonRegister.getBranchTypeKeyboard());
        } else {
            sendMessage.setReplyMarkup(SpecialityButtonRegister.getBranchTypeKeyboard());
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        }
        sendMessage.setText("Тип галузі:");
        return sendMessage;
    }


    public SendMessage handleBranchType(Update update, Map<String, String> callback) {

        var branches = Filter.getBranchesOfKnowledgeByType(callback.get("text"));
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        InlineKeyboardMarkup keyboard = SpecialityButtonRegister.getBranchOfKnowledgeKeyboard(branches,callback);
        sendMessage.setText(callback.get("text")+" галузі:");
        sendMessage.setReplyMarkup(keyboard);

        return sendMessage;
    }

    public SendMessage handleBranchOfKnowledge(Update update, Map<String, String> callback, SubjectHandler subjectHandler) {
        cleanRequests();

        if(isReturnButtonPressed(callback)){
            if(EnumSetUtil.isEnumSet(callback.get("previous"))){
                return subjectHandler.handleReturn(update);
            } else {
                return handle(update);
            }
        }

        var specialties = Filter.getSpecialitiesByBranchName(callback.get("text"));

        String branchOfKnowledge = Filter.getBranchOfKnowledgeName(callback.get("text"));
        InlineKeyboardMarkup inlineKeyboardMarkup = SpecialityButtonRegister.getSpecialtyKeyboard(specialties, callback);
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Спеціальності по галузі "+branchOfKnowledge+" :");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage handleSpeciality(Update update, Map<String, String> callback) {
        cleanRequests();

        if(isReturnButtonPressed(callback)){
            if(EnumSetUtil.isEnumSet(callback.get("root"))){
                var enumSet = EnumSetUtil.decode(Integer.parseInt(callback.get("root")), Subject.class);
                return handleFiltered(update, enumSet);
            } else {
                callback.put("text", callback.get("root"));
                return handleBranchType(update,callback);
            }
        }

        String branchName = Filter.getBranchOfKnowledgeName(callback.get("previous"));
        Specialty specialty = Filter.getSpecialtyByName(callback.get("text"));
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText(SpecialityButtonRegister.getSubjectsText(specialty,branchName));
        sendMessage.setReplyMarkup(SpecialityButtonRegister.getSubjectsKeyboard());

        return sendMessage;
    }

    public SendMessage handleFiltered(Update update, EnumSet<Subject> enumSet) {
        message = update.getCallbackQuery().getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        var branches = Filter.getFiltered(enumSet);
        var branchesOfKnowledge = Filter.getBranchesByName(branches);
        sendMessage.setReplyMarkup(SpecialityButtonRegister.getBranchOfKnowledgeKeyboard(branchesOfKnowledge,enumSet));
        sendMessage.setText("Галузі по вибраним предметам: ");
        return sendMessage;
    }

    protected static boolean isReturnButtonPressed(Map<String, String> callback){
        return callback.get("text").equalsIgnoreCase("Назад");
    }


}
