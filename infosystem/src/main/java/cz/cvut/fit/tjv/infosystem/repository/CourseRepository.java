package cz.cvut.fit.tjv.infosystem.repository;

import cz.cvut.fit.tjv.infosystem.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

}
