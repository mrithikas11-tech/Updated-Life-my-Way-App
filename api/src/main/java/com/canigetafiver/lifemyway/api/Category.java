package com.canigetafiver.lifemyway.api;

public enum Category {
    ENTERTAINMENT,
    DINING,
    FOOD,
    GROCERY,
    HEALTH,
    RENT,
    TRANSPORTATION,
    UTILITIES,
    OTHER;

    public String displayName() {
        String s = name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
