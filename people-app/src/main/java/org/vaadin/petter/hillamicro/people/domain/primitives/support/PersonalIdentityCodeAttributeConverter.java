package org.vaadin.petter.hillamicro.people.domain.primitives.support;

import org.vaadin.petter.hillamicro.people.domain.primitives.PersonalIdentityCode;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PersonalIdentityCodeAttributeConverter implements AttributeConverter<PersonalIdentityCode, String> {

    @Override
    public String convertToDatabaseColumn(PersonalIdentityCode attribute) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToDatabaseColumn'");
    }

    @Override
    public PersonalIdentityCode convertToEntityAttribute(String dbData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToEntityAttribute'");
    }
    
}
