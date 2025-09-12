package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Person;
import cz.spgsasoskladno.infosystem.domain.UserType;
import cz.spgsasoskladno.infosystem.exception.BusinessRuleViolation;
import cz.spgsasoskladno.infosystem.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl extends CrudServiceImpl<Person, Integer> implements PersonService {

    private final PersonRepository personRepository;

    @Override
    protected JpaRepository<Person, Integer> getRepository() {
        return personRepository;
    }

    @Override
    public Person studentValidator(Integer personId) {
        Person person = readById(personId, "Person")
        if ( person.getRole() != UserType.STUDENT )
            throw new BusinessRuleViolation("Person with id " + personId + " is not a student.");
        return person;
    }

}
