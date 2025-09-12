package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
