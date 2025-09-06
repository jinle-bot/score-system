package cz.cvut.fit.tjv.infosystem.repository;

import cz.cvut.fit.tjv.infosystem.domain.Activity;
import cz.cvut.fit.tjv.infosystem.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    boolean existsByIdAndSubjectId(Integer id, Integer subjectId);
    Optional<Activity> findByIdAndSubjectId(Integer id, Integer subjectId);

    Collection<Activity> findAllBySubject(Subject subject);
    List<Activity> findAllBySubjectId(Integer subjectId);

    @Query("SELECT a FROM Activity a WHERE a.mandatory = TRUE AND a.subject.id = :subjectId order by a.priority")
    Collection<Activity> findMandatoryActivitiesBySubject(@Param("subjectId") Integer subjectId);

}
