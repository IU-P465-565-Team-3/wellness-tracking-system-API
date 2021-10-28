package com.wellness.tracking.enums;

public enum ContentType {
    TEXT(Constants.TEXT),
    IMAGE(Constants.IMAGE),
    VIDEO(Constants.VIDEO);

    private final String value;

    ContentType(String value) {
        try {
            if (!value.equals(Constants.class.getField(this.name()).get(null))) {
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
        public static final String TEXT = "TEXT";
        public static final String IMAGE = "IMAGE";
        public static final String VIDEO = "VIDEO";
    }
}