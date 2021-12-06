package com.softserve.bot.view.handler;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import com.softserve.bot.service.Filter;
import com.softserve.bot.util.SpecialityButtonRegister;
import com.softserve.bot.view.TelegramFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Component
public class SpecializationHandler implements Handler {
    private Map<String, List<Specialty>> filteredSpecialty;
    private Message message;
    private SendMessage sendMessage;

    private void cleanRequests() {
        sendMessage.setReplyMarkup(null);
    }


    public SendMessage handle(Update update, Set<Subject> enumSet) {
        cleanRequests();
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Cпеціальності:from search");
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


    public SendMessage handleBranchType(Update update, String branchType) {

        var branches = Filter.getBranchesOfKnowledgeByType(branchType);
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Галузі:");
        InlineKeyboardMarkup keyboard = SpecialityButtonRegister.getBranchOfKnowledgeKeyboard(branches,branchType);
        sendMessage.setReplyMarkup(keyboard);

        return sendMessage;
    }

    public SendMessage handleBranchOfKnowledge(Update update, Map<String, String> callback, SubjectHandler subjectHandler) {
        cleanRequests();

        if(callback.get("text").equals("Назад")){
            if(callback.get("branch type").equalsIgnoreCase("Sub selection"))
            {
                return subjectHandler.handleReturn(update);
            }
            else{
                return handle(update);
            }
        }

        var specialties = Filter.getSpecialitiesByBranchName(callback.get("text"));

        InlineKeyboardMarkup inlineKeyboardMarkup = SpecialityButtonRegister.getSpecialtyKeyboard(specialties, callback.get("branch type"));
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText("Спеціальності:");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage handleSpeciality(Update update, Map<String, String> callback) {
        cleanRequests();

        if(callback.get("text").equals("Назад")){
            return handleBranchType(update,callback.get("branch type"));
        }

        Specialty specialty = Filter.getSpecialtyByName(callback.get("text"));
        sendMessage.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        sendMessage.setText(SpecialityButtonRegister.getSubjectsText(specialty));
        sendMessage.setReplyMarkup(null);

        return sendMessage;
    }

    public SendMessage handleFiltered(Update update, EnumSet<Subject> enumSet) {
        message = update.getCallbackQuery().getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        var branches = Filter.getFiltered(enumSet);
        System.out.println(filteredSpecialty);
        var branchesOfKnowledge = Filter.getBranchesByName(filteredSpecialty);
        System.out.println(branchesOfKnowledge);
//        StringBuilder stringBuilder = new StringBuilder();
//        for (List<Specialty> value : filteredSpecialty.values()) {
//            for (Specialty currentSpecialty : value) {
//                stringBuilder.append(currentSpecialty).append("\n");
//            }
//        }

        sendMessage.setReplyMarkup(SpecialityButtonRegister.getBranchOfKnowledgeKeyboard(branchesOfKnowledge));
        sendMessage.setText("Галузі: ");
        return sendMessage;
    }
}
