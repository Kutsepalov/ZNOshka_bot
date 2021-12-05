package com.softserve.bot.model;

public enum Subject {
    UKRAINIAN("Українська мова"),
    LITERATURE("Українська мова і література"),
    MATH_STANDARD("Математика (стандартна)"),
    MATH_PROFILE("Математика (профільна)"),
    HISTORY("Історія України"),
    FOREIGN("Іноземна мова", 1),
    ENGLISH("Англійська мова", 1),
    SPANISH("Іспанська мова", 1),
    GERMANY("Німецька мова", 1),
    FRENCH("Французька мова", 1),
    BIOLOGY("Біологія"),
    GEOGRAPHY("Географія"),
    PHYSICS("Фізика"),
    CHEMISTRY("Хімія"),
    CREATIVE_COMPETITION("Творчий конкурс");

    private final String name;
    private final int priority;

    Subject(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    Subject(String name) {
        this(name, 0);
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

    public int getPriority() {
        return priority;
    }
}