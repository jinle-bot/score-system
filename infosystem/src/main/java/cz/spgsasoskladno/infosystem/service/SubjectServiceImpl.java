package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Subject;
import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.domain.UserType;
import cz.spgsasoskladno.infosystem.repository.SubjectRepository;
import cz.spgsasoskladno.infosystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class SubjectServiceImpl extends CrudServiceImpl<Subject, Integer> implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubjectServiceImpl (SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected Subject checkAndPut (Subject subject) {
        if ( subject.getName() == null || subject.getName().isEmpty() ||
             subject.getSchoolYear() == null || subject.getSchoolYear().isEmpty() ||
             subject.getSemester() == null ) {
            throw new IllegalArgumentException("Subject must have name, school year and semester.");
        }
        if (subjectRepository.existsByNameAndSchoolYearAndSemester(subject.getName(), subject.getSchoolYear(), subject.getSemester())) {
            throw new EntityExistsException("This run of subject already exists");
        }
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject addStudentIntoSubject(Integer studentId, Integer subjectId) {
        Optional<User> optUser = userRepository.findById(studentId);
        Optional<Subject> optSubject = subjectRepository.findById(subjectId);
        if (optUser.isEmpty() || optSubject.isEmpty()) {
            throw new IllegalArgumentException("Invalid User or Subject ID");
        }

        User user = optUser.get();
        if ( user.getUserType() != UserType.STUDENT )
            throw new IllegalArgumentException("User is not a student");

        Subject subject = optSubject.get();

        if ( ! subject.getStudents().add(user) )
            throw new EntityExistsException("Student already in subject");

        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getSubjectsByStudentId(Integer studentId) {
        Optional<User> optUser = userRepository.findById(studentId);
        if (optUser.isEmpty())
            throw new IllegalArgumentException("Invalid User ID");
        User user = optUser.get();
        return subjectRepository.findByStudentId(user.getId());
    }


    @Override
    protected JpaRepository<Subject, Integer> getRepository() {
        return subjectRepository;
    }
}
