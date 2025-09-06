package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.*;
import cz.cvut.fit.tjv.infosystem.repository.ActivityRepository;
import cz.cvut.fit.tjv.infosystem.repository.GradeRepository;
import cz.cvut.fit.tjv.infosystem.repository.SubjectRepository;
import cz.cvut.fit.tjv.infosystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl extends CrudServiceImpl<Grade, Integer> implements GradeService {

    private final GradeRepository gradeRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository,
                            ActivityRepository activityRepository,
                            UserRepository userRepository,
                            SubjectRepository subjectRepository,
                            SubjectService subjectService) {
        this.gradeRepository = gradeRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

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
