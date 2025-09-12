package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.Enrollment;
import cz.spgsasoskladno.infosystem.domain.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    @Query(value= """
            SELECT 1
            FROM enrollment e
            WHERE e.person_id = :personId
              AND e.course_id = :courseId
              AND e.is_active = true
            """, nativeQuery = true)
    boolean isPersonActiveEnrolledInCourse(@Param("personId") Integer personId,
                                           @Param("courseId") Integer courseId);

    @Query(value= """
            SELECT 1
            FROM enrollment e
            WHERE e.person_id = :personId
              AND e.course_id = :courseId
            """, nativeQuery = true)
    boolean isPersonEnrolledInCourse(@Param("personId") Integer personId,
                                     @Param("courseId") Integer courseId);

    Optional<Enrollment> findByIdPersonIdAndIdCourseId(Integer personId, Integer courseId);

    @Query("""
           select e from Enrollment e
           join fetch e.person p
           join fetch e.course c
           where e.id.personId = :personId and e.id.courseId = :courseId
           """)
    Optional<Enrollment> findWithPersonAndCourse(Integer personId, Integer courseId);

    @Query("""
           select e from Enrollment e
           join fetch e.person p
           where e.id.personId = :personId and e.id.courseId = :courseId
           """)
    Optional<Enrollment> findWithPersonOnly(Integer personId, Integer courseId);

    @Query("""
           select e from Enrollment e
           join fetch e.course c
           where e.id.personId = :personId and e.id.courseId = :courseId
           """)
    Optional<Enrollment> findWithCourseOnly(Integer personId, Integer courseId);

}

