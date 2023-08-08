package org.vaadin.petter.hillamicro.people.domain.model;

import java.util.List;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.vaadin.petter.hillamicro.people.domain.primitives.PersonName;
import org.vaadin.petter.hillamicro.people.domain.primitives.PersonalIdentityCode;
import org.vaadin.petter.hillamicro.people.domain.primitives.support.PersonNameAttributeConverter;
import org.vaadin.petter.hillamicro.people.domain.primitives.support.PersonalIdentityCodeAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

@Entity
public class Person extends AbstractPersistable<Long> {
    
    @Convert(converter = PersonalIdentityCodeAttributeConverter.class)
    @Column(unique = true, nullable = false)
    private PersonalIdentityCode pic;

    @Convert(converter = PersonNameAttributeConverter.class)
    @Column(nullable = false)
    private PersonName surname;

    @Convert(converter = PersonNameAttributeConverter.class)
    @ElementCollection
    private List<PersonName> givenNames;


}
