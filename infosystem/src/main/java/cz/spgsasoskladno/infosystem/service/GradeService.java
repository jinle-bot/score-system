package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Grade;
import cz.spgsasoskladno.infosystem.dto.grade.CourseGradesResponseDto;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface GradeService extends CrudService<Grade, Integer> {

    Collection<Grade> getAllMandatoryAndGivenGradesBySubjectIdAndStudentId(Integer subjectId, Integer studentId);
    Collection<Grade> getAllGivenGradesBySubjectIdAndStudentId(Integer subjectId, Integer studentId);
    Collection<Grade> getAllGivenGradesByActivityId(Integer activityId);

    CourseGradesResponseDto getCourseGradesByStudentAndCourse(Integer personId, Integer courseId);


}
