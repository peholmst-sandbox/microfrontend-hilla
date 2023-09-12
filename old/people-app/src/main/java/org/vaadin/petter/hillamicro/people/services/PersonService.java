package org.vaadin.petter.hillamicro.people.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.petter.hillamicro.people.domain.model.Person;

@Service
public class PersonService {
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<Person> findPeople(PersonQuery query) {
        // TODO Implement me!
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // TODO Create and update 
}
