package cz.cvut.fit.tjv.infosystem.repository;

import cz.cvut.fit.tjv.infosystem.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
