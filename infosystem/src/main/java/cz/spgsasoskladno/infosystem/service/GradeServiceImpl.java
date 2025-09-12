package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.*;
import cz.spgsasoskladno.infosystem.dto.grade.CourseGradesResponseDto;
import cz.spgsasoskladno.infosystem.mapper.CourseGradesMapper;
import cz.spgsasoskladno.infosystem.repository.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GradeServiceImpl extends CrudServiceImpl<Grade, Integer> implements GradeService {

    private final GradeRepository gradeRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final PersonService personService;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentService enrollmentService;
    private final ActivityGradeRepository activityGradeRepository;
    private final CourseGradeRepository courseGradeRepository;
    private final CourseGradesMapper courseGradesMapper;


    @Override
    @Transactional
    protected Grade checkAndPut (Grade grade) {
        if ( grade.getStudent() == null || grade.getStudent().getId() == null ||
                grade.getActivity() == null || grade.getActivity().getId() == null )
            throw new IllegalArgumentException("Grade must have valid Student and Activity");
        if ( gradeRepository.existsByStudentIdAndActivityId(grade.getStudent().getId(), grade.getActivity().getId()) )
            throw new EntityExistsException("Grade already exists");
        Optional<User> optUser = userRepository.findById(grade.getStudent().getId());
        if ( optUser.isEmpty() )
            throw new EntityNotFoundException("User for which grade is given not found");
        User user = optUser.get();
        if ( user.getUserType() != UserType.STUDENT )
            throw new IllegalArgumentException("Grade can be given only to students");
        Optional<Activity> optActivity = activityRepository.findById(grade.getActivity().getId());
        if ( optActivity.isEmpty() )
            throw new EntityNotFoundException("Activity for which grade is given not found");
        Activity activity = optActivity.get();
        if ( ! activity.getSubject().getStudents().contains(user) )
            throw new IllegalArgumentException("The student does not belong the subject");
        return gradeRepository.save(grade);
    }

    @Override
    public Collection<Grade> getAllMandatoryAndGivenGradesBySubjectIdAndStudentId(Integer subjectId, Integer studentId) {
        checkSubjectAndStudent(subjectId, studentId);
        return gradeRepository.findAllMandatoryAndGivenGradesBySubjectIdAndStudentId(subjectId, studentId);
    }

    @Override
    public Collection<Grade> getAllGivenGradesBySubjectIdAndStudentId(Integer subjectId, Integer studentId) {
        checkSubjectAndStudent(subjectId, studentId);
        return gradeRepository.findByStudentIdAndActivitySubjectId(studentId, subjectId);
    }

    @Override
    public Collection<Grade> getAllGivenGradesByActivityId(Integer activityId) {
        return gradeRepository.findByActivityIdOrderByStudent(activityId);
    }

    @Override
    public CourseGradesResponseDto getCourseGradesByStudentAndCourse(Integer personId, Integer courseId) {
        Course course = courseService.readById(courseId, "Course");
        Person student = personService.studentValidator(personId);
        enrollmentService.activeEnrollmentValidator(personId, courseId);

        List<ActivityGrade> activityGrades =
                activityGradeRepository.findByCourseAndStudent(course.getId(), student.getId());
        CourseGrade courseGrade = courseGradeRepository.findOpenByCourseAndPerson(course.getId(), student.getId())
                .orElseThrow(() -> new EntityNotFoundException()); //???

        return courseGradesMapper.toDto(course, activityGrades, courseGrade);
    }

    @Override
    protected JpaRepository<Grade, Integer> getRepository() {
        return gradeRepository;
    }

    private void checkSubjectAndStudent(Integer subjectId, Integer studentId) {
        Optional<Subject> optSubject = subjectRepository.findById(subjectId);
        Optional<User> optUser = userRepository.findById(studentId);

        if (optSubject.isEmpty() || optUser.isEmpty()) {
            throw new IllegalArgumentException("Subject or student not found");
        }

        User user = optUser.get();
        if ( user.getUserType() != UserType.STUDENT )
            throw new IllegalArgumentException("User is not a student");

        Subject subject = optSubject.get();
        if ( ! subject.getStudents().contains(user) )
            throw new EntityNotFoundException("Student does not have the subject");
    }



}
