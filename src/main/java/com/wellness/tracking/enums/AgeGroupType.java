package com.wellness.tracking.enums;

import java.util.Arrays;

public enum AgeGroupType {
    A(20, 30, Constants.A),
    B(30, 40, Constants.B),
    C(40, 50, Constants.C),
    D(50, 60, Constants.D);

    private final int min;
    private final int max;
    private final String name;

    AgeGroupType(int min, int max, String name) {
        this.min = min;
        this.max = max;
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }

    public static AgeGroupType get(int age) {
        return Arrays.stream(values()).filter(i -> age >= i.min && age < i.max).findFirst().orElse(null);
    }

    public static class Constants {
        public static final String A = "A";
        public static final String B = "B";
        public static final String C = "C";
        public static final String D = "D";
    }
}
