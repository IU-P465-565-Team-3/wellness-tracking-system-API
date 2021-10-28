package com.wellness.tracking.enums;

public enum ListingType {
    FITNESS_PLAN(Constants.FITNESS_PLAN),
    MULTIMEDIA_POST(Constants.MULTIMEDIA_POST);

    private final String value;

    ListingType(String value) {
        try {
            if (!value.equals(ListingType.Constants.class.getField(this.name()).get(null))) {
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
        public static final String FITNESS_PLAN = "FITNESS_PLAN";
        public static final String MULTIMEDIA_POST = "MULTIMEDIA_POST";
    }
}