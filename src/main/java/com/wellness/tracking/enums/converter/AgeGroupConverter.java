package com.wellness.tracking.enums.converter;

import com.wellness.tracking.enums.AgeGroupType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AgeGroupConverter implements AttributeConverter<AgeGroupType, String> {

    @Override
    public String convertToDatabaseColumn(AgeGroupType attribute) {
        if(attribute == null)
            return null;
        return attribute.getValue();
    }

    @Override
    public AgeGroupType convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        return Stream.of(AgeGroupType.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
