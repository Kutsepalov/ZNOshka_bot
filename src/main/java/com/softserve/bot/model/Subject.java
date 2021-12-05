package com.softserve.bot.model;

public enum Subject {
    UKRAINIAN("Українська мова"),
    LITERATURE("Українська мова і література"),
    MATH_STANDARD("Математика (стандартна)"),
    MATH_PROFILE("Математика (профільна)"),
    HISTORY("Історія України"),
    ENGLISH("Англійська мова"),
    SPANISH("Іспанська мова"),
    GERMANY("Німецька мова"),
    FRENCH("Французька мова"),
    BIOLOGY("Біологія"),
    GEOGRAPHY("Географія"),
    PHYSICS("Фізика"),
    CHEMISTRY("Хімія"),
    CREATIVE_COMPETITION("Творчий конкурс");

    private String name;

    Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String element) {
        for (Subject subject : Subject.values()) {
            if (subject.name().equals(element))
                return true;
        }
        return false;
    }

}
