package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

}
