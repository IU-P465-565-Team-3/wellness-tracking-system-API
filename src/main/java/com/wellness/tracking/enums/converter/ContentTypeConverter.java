package com.wellness.tracking.enums.converter;

import com.wellness.tracking.enums.ContentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ContentTypeConverter implements AttributeConverter<ContentType, String> {

    @Override
    public String convertToDatabaseColumn(ContentType type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }

    @Override
    public ContentType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return Stream.of(ContentType.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}