package com.wellness.tracking.enums.converter;

import com.wellness.tracking.enums.ListingType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ListingTypeConverter implements AttributeConverter<ListingType, String> {

    @Override
    public String convertToDatabaseColumn(ListingType type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }

    @Override
    public ListingType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Stream.of(ListingType.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}