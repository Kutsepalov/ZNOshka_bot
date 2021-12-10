package com.softserve.bot.util;

import com.softserve.bot.model.BranchOfKnowledge;
import com.softserve.bot.model.Specialty;
import com.softserve.bot.model.Subject;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SpecialityButtonRegister {

    private static final String BRANCH_TYPE = "Branch type";
    private static final String BRANCH = "Branch";
    private static final String RETURN = "Назад";
    private static final String SPECIALTY = "Speciality";
    private static final String MORE_INFORMATION = "Дізнатися більше";

    public static InlineKeyboardMarkup getBranchTypeKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        String[] branchTypes=new String[]{"Гуманітарні" ,"Технічні"};
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        for (var type:branchTypes){
            var row = getSingleButtonRow(type,buildCallback(BRANCH_TYPE,type));
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getBranchOfKnowledgeKeyboard(Collection<BranchOfKnowledge> branchesOfKnowledge, Map<String, String> callback){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        for (var branch:branchesOfKnowledge){
            String buttonName = branch.getTitle();
            String nextCallback = buildCallback(BRANCH, branch.getTitle().substring(0,4), callback.get("text"));
            var row = getSingleButtonRow(buttonName,nextCallback);
            rows.add(row);
        }
        String nextCallback = buildCallback(BRANCH, RETURN, callback.get("text"));
        var row = getSingleButtonRow(RETURN, nextCallback);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getSpecialtyKeyboard(Collection<Specialty> specialties, Map<String,String> callback){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        for (var specialty:specialties){
            String buttonName = specialty.getName();
            String nextCallback = buildCallback(SPECIALTY, specialty.getCode(),callback.get("text") ,callback.get("previous"));
            var row = getSingleButtonRow(buttonName,nextCallback);
            rows.add(row);
        }
        String nextCallback = buildCallback(SPECIALTY, RETURN, callback.get("text"), callback.get("previous"));
        var row = getSingleButtonRow(RETURN, nextCallback);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static String getSubjectsText(Specialty specialty,String branchName){
        String branchOfKnowledge = "Галузь: " + branchName;
        String spec = "Спеціальність: "+ specialty.getCode()+" "+ specialty.getName();
        String first = "1) "+ specialty.getFirst().getName();
        String second = "2) "+ subjectCollectionToString(specialty.getSecond());
        String third = "3) "+ subjectCollectionToString(specialty.getThird());

        return String.join("\n", branchOfKnowledge, spec, first, second, third);
    }

    public static InlineKeyboardMarkup getSubjectsKeyboard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();

        String nextCallback = buildCallback(MORE_INFORMATION, MORE_INFORMATION);
        var row = getSingleButtonRow(MORE_INFORMATION, nextCallback);

        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getBranchOfKnowledgeKeyboard(Collection<BranchOfKnowledge> branchesOfKnowledge,EnumSet<Subject> enumSet){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows= new ArrayList<>();
        String enumCode = String.valueOf(EnumSetUtil.code(enumSet));
        for (var branch:branchesOfKnowledge){
            String buttonName = branch.getTitle();
            String nextCallback = buildCallback(BRANCH, branch.getTitle().substring(0,4), enumCode);
            var row = getSingleButtonRow(buttonName,nextCallback);
            rows.add(row);
        }
        String nextCallback = buildCallback(BRANCH, RETURN, enumCode);
        var row = getSingleButtonRow(RETURN, nextCallback);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return  inlineKeyboardMarkup;
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

    protected static String subjectCollectionToString(Collection<Subject> subjects) {
        return subjects
                .stream()
                .map(subject -> subject.getName())
                .collect(Collectors.joining(" або "));
    }


}
