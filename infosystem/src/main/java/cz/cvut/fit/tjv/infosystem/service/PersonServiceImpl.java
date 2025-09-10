package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Person;
import cz.cvut.fit.tjv.infosystem.domain.UserType;
import cz.cvut.fit.tjv.infosystem.exception.BusinessRuleViolation;
import cz.cvut.fit.tjv.infosystem.exception.NotFoundException;
import cz.cvut.fit.tjv.infosystem.repository.PersonRepository;
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
