package com.course.entity;

public enum CapitalizationType {
    NO("NO"),
    MONTHLY("MONTHLY"),
    YEARLY("YEARLY");

    private final String value;

    CapitalizationType(String url) {
        this.value = url;
    }

    public String getType() {
        switch (value) {
            case "NO" : {
                return "Нет";
            }
            case "MONTHLY": {
                return "Месячная";
            }
            case "YEARLY": {
                return "Годовая";
            }
        }
        return null;
    }
}
