package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.Activity;
import cz.spgsasoskladno.infosystem.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Collection<Grade> findByActivity(Activity activity);

    Collection<Grade> findByActivityIdOrderByStudent(Integer activityId);
    Collection<Grade> findByStudentIdAndActivitySubjectId(Integer studentId, Integer subjectId);

    boolean existsByStudentIdAndActivityId(Integer studentId, Integer activityId);
    boolean existsByIdAndStudentIdAndActivityId(Integer id, Integer studentId, Integer activityId);

    @Query( value = """
                    ( SELECT g.*
                      FROM activity a LEFT JOIN grade g ON a.id = g.activity_id AND g.student_id = :studentID
                      WHERE a.subject_id = :subjectId AND a.mandatory = TRUE )
                    UNION
                    ( SELECT g.*
                      FROM activity a JOIN grade g ON a.id = g.activity_id AND g.student_id = :studentID
                      WHERE a.subject_id = :subjectId )
                    ORDER BY a.priority;
                   """, nativeQuery = true)
    Collection<Grade> findAllMandatoryAndGivenGradesBySubjectIdAndStudentId
            ( @Param("subjectId") Integer subjectId,
              @Param("studentId") Integer studentId );
}
