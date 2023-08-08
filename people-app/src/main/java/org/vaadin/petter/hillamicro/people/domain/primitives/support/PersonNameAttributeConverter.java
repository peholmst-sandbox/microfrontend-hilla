package org.vaadin.petter.hillamicro.people.domain.primitives.support;

import org.vaadin.petter.hillamicro.people.domain.primitives.PersonName;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PersonNameAttributeConverter implements AttributeConverter<PersonName, String> {

    @Override
    public String convertToDatabaseColumn(PersonName attribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToDatabaseColumn'");
    }

    @Override
    public PersonName convertToEntityAttribute(String dbData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntityAttribute'");
    }
    
}
