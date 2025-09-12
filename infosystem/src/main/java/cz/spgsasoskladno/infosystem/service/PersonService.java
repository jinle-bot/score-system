package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Person;
import org.springframework.stereotype.Service;


@Service
public interface PersonService extends CrudService<Person, Integer> {
    Person studentValidator(Integer personId);
}
