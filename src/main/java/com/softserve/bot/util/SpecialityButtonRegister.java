package com.softserve.bot.util;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpecialityButtonRegister {
    public static InlineKeyboardMarkup getBranchTypeKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        String[] branchTypes=new String[]{"Гуманітарні" ,"Технічні"};
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        for (var branchType:branchTypes){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(branchType);
            button.setCallbackData("Branch type/"+branchType);
            List<InlineKeyboardButton> row= new ArrayList<>();
            row.add(button);
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getBranchOfKnowledgeKeyboard(Collection<BranchOfKnowledge> branchesOfKnowledge, String branchType){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        for (var branch:branchesOfKnowledge){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(branch.getTitle());
            button.setCallbackData("Branch/"+branch.getTitle().substring(0,4)+"/"+branchType);
            List<InlineKeyboardButton> row= new ArrayList<>();
            row.add(button);
            rows.add(row);
        }
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Назад");
        button.setCallbackData("Branch/Назад/"+branchType);
        List<InlineKeyboardButton> row= new ArrayList<>();
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getSpecialtyKeyboard(Collection<Specialty> specialties, String branchType){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        for (var specialty:specialties){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(specialty.getName());
            button.setCallbackData("Speciality/"+specialty.getCode()+"/"+branchType);
            List<InlineKeyboardButton> row= new ArrayList<>();
            row.add(button);
            rows.add(row);
        }

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Назад");
        button.setCallbackData("Speciality/Назад/"+branchType);
        List<InlineKeyboardButton> row= new ArrayList<>();
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static String getSubjectsText(Specialty specialty){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        StringBuilder answer = new StringBuilder();
        answer.append("Спеціальність: "+ specialty.getCode()+" "+ specialty.getName()+"\n");
        answer.append(specialty.getFirst().getName()+"\n");
        for (var subject:specialty.getSecond()){
            answer.append(subject.getName());
            answer.append(" або ");
        }

        answer.replace(answer.length() - 5,answer.length(),"\n");

        for (var subject:specialty.getThird()){
            answer.append(subject.getName());
            answer.append(" або ");
        }

        answer.replace(answer.length() - 5,answer.length(),"\n");

        return answer.toString();
    }

    public static InlineKeyboardMarkup getBranchOfKnowledgeKeyboard(Collection<BranchOfKnowledge> branchesOfKnowledge){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        for (var branch:branchesOfKnowledge){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(branch.getTitle());
            button.setCallbackData("Branch/"+branch.getTitle().substring(0,4)+"/"+"Sub selection");
            List<InlineKeyboardButton> row= new ArrayList<>();
            row.add(button);
            rows.add(row);
        }
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Назад");
        button.setCallbackData("Branch/Назад/"+"Sub selection");
        List<InlineKeyboardButton> row= new ArrayList<>();
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return  inlineKeyboardMarkup;
    }
}