package cz.cvut.fit.tjv.infosystem.repository;

import cz.cvut.fit.tjv.infosystem.domain.CourseGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Repository
@Transactional(readOnly = true)
public interface CourseGradeRepository extends Repository<CourseGrade, Integer> {

    // --- Single row: open grade for given (course, person) ---
    @Query(
            value = """
                SELECT cg.*
                  FROM course_grade cg
                 WHERE cg.course_id = :courseId
                   AND cg.person_id = :personId
                   AND cg.valid_to IS NULL
                """,
            nativeQuery = true
    )
    Optional<CourseGrade> findOpenByCourseAndPerson(@Param("courseId") Integer courseId,
                                                    @Param("personId") Integer personId);

    // --- All open grades for a given person ---
    @Query(
            value = """
                SELECT cg.*
                  FROM course_grade cg
                 WHERE cg.person_id = :personId
                   AND cg.valid_to IS NULL
                 ORDER BY cg.course_id, cg.id
                """,
            nativeQuery = true
    )
    List<CourseGrade> findAllOpenByPerson(@Param("personId") Integer personId);

    // --- All open grades for a given course ---
    @Query(
            value = """
                SELECT cg.*
                  FROM course_grade cg
                 WHERE cg.course_id = :courseId
                   AND cg.valid_to IS NULL
                 ORDER BY cg.person_id, cg.id
                """,
            nativeQuery = true
    )
    List<CourseGrade> findAllOpenByCourse(@Param("courseId") Integer courseId);

    // --- Standard reader by PK (useful sometimes) ---
    @Query(
            value = "SELECT cg.* FROM course_grade cg WHERE cg.id = :id",
            nativeQuery = true
    )
    Optional<CourseGrade> findById(@Param("id") Integer id);

}
