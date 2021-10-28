package com.wellness.tracking.enums;

public enum Role {
    ADMIN(Constants.ADMIN),
    CLIENT(Constants.CLIENT),
    PROFESSIONAL(Constants.PROFESSIONAL);

    private final String value;

    Role(String value) {
        try {
            if (!value.equals(Role.Constants.class.getField(this.name()).get(null))) {
                throw new IllegalArgumentException(value);
            }
            this.value = value;
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new IllegalArgumentException(this.name());
        }
    }

    public String getValue() {
        return this.value;
    }

    public static class Constants {
        public static final String ADMIN = "ADMIN";
        public static final String CLIENT = "CLIENT";
        public static final String PROFESSIONAL = "PROFESSIONAL";
    }
}