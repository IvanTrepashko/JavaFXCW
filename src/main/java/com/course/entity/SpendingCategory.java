package com.course.entity;

import java.util.HashMap;
import java.util.Map;

public enum SpendingCategory {
    FOOD(1),
    HCS(2),
    TRANSPORT(3),
    ENTERTAINMENT(4),
    CREDIT(5),
    FUEL(6),
    SUBSCRIPTION(7),
    CLOTHES(8),
    OTHER(9);

    private final int value;
    private final static Map map = new HashMap<>();

    SpendingCategory(int value) {
        this.value = value;
    }

    static {
        for (SpendingCategory spendingCategory : SpendingCategory.values()) {
            map.put(spendingCategory.value, spendingCategory);
        }
    }

    public static SpendingCategory valueOf(int spendingCategory) {
        return (SpendingCategory) map.get(spendingCategory);
    }

    public int getValue() {
        return value;
    }
}
