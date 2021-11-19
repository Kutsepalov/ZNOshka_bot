package com.softserve.webhookbot.enumeration;

public enum Subject {
    UKRAINIAN("Українська мова"),
    LITERATURE("Українська мова і література"),
    MATHSTANDART("Математика (рівня стандарту)"),
    MATHPROFILE("Математика (рівня профільний)"),
    HISTORY("Історія України"),
    ENGLISH("Англійська мова"),
    SPANISH("Іспанська мова"),
    GERMANY("Німецька мова"),
    FRENCH("Французька мова"),
    BIOLOGY("Біологія"),
    GEOGRAPHY("Географія"),
    PHYSICS("Фізика"),
    CHEMISTRY("Хімія"),
    CREATIVECOMPETITION("Творчий конкурс");

    private String name;

    Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
