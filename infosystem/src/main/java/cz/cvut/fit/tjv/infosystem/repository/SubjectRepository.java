package cz.cvut.fit.tjv.infosystem.repository;

import cz.cvut.fit.tjv.infosystem.domain.Subject;
import cz.cvut.fit.tjv.infosystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByNameAndSchoolYearAndSemester(String name, String schoolYear, Integer semester);

    @Query( value = """
                    SELECT su.*
                    FROM student_subject ss JOIN subject su ON ss.subject_id = su.id
                    WHERE ss.student_id = :studentId;
                    """, nativeQuery = true )
    List<Subject> findByStudentId(@Param("studentId") Integer studentId);
}
