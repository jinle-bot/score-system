package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Person;
import org.springframework.stereotype.Service;


@Service
public interface PersonService extends CrudService<Person, Integer> {
    Person studentValidator(Integer personId);
}
