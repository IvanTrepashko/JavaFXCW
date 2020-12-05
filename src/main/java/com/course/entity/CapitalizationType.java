package com.course.entity;

import java.util.HashMap;
import java.util.Map;

public enum CapitalizationType {
    NO(1),
    MONTHLY(2),
    YEARLY(3);

    private int value;
    private static Map map = new HashMap<>();

    private CapitalizationType(int value) {
        this.value = value;
    }

    static {
        for (CapitalizationType pageType : CapitalizationType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static CapitalizationType valueOf(int pageType) {
        return (CapitalizationType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }
}
