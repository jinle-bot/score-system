package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.ActivityGrade;
import cz.spgsasoskladno.infosystem.domain.GradeStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Repository
public interface ActivityGradeRepository extends Repository<ActivityGrade, Integer> {

    @Query(value= """
        SELECT *
        FROM activity_grade
        WHERE activity_id = :activityId 
          AND person_id = :studentId 
          AND valid_to IS NULL 
    """, nativeQuery = true)
    Optional<ActivityGrade> findByActivityAndStudent(@Param("activityId") Integer activityId,
                                                     @Param("studentId") Integer studentId);

    @Query("""
        select ag
        from ActivityGrade ag
        join fetch ag.activity
        where ag.person.id = :studentId
            and ag.validTo is null
            and ag.activity.course.id = :courseId
    """)
    List<ActivityGrade> findByCourseAndStudent(@Param("courseId") Integer courseId,
                                               @Param("studentId") Integer studentId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        SELECT * 
        FROM update_activity_grade(:activityId, :personId, :points, :actDate, :repairs, DEFAULT)
    """, nativeQuery = true)
    ActivityGrade setPoints(@Param("activityId") Integer activityId,
                            @Param("personId") Integer personId,
                            @Param("points") Integer points,
                            @Param("repairs") Integer repairs,
                            @Param("actDate") LocalDate actDate);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        SELECT * 
        FROM update_activity_grade(:activityId, :personId, :points, DEFAULT, :repairs, DEFAULT)
    """, nativeQuery = true)
    ActivityGrade setPoints(@Param("activityId") Integer activityId,
                            @Param("personId") Integer personId,
                            @Param("points") Integer points,
                            @Param("repairs") Integer repairs);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        SELECT * 
        FROM update_activity_grade(:activityId, :personId, DEFAULT, :actDate, DEFAULT, :status)
    """, nativeQuery = true)
    ActivityGrade setStatus(@Param("activityId") Integer activityId,
                            @Param("personId") Integer personId,
                            @Param("status") GradeStatus status,
                            @Param("actDate") LocalDate actDate);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        SELECT * 
        FROM update_activity_grade(:activityId, :personId, DEFAULT, DEFAULT, DEFAULT, :status)
    """, nativeQuery = true)
    ActivityGrade setStatus(@Param("activityId") Integer activityId,
                            @Param("personId") Integer personId,
                            @Param("status") GradeStatus status);

}
