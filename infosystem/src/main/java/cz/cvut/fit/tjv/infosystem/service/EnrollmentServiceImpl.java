package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Enrollment;
import cz.cvut.fit.tjv.infosystem.domain.EnrollmentId;
import cz.cvut.fit.tjv.infosystem.exception.BusinessRuleViolation;
import cz.cvut.fit.tjv.infosystem.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CrudServiceImpl<Enrollment, EnrollmentId> implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    protected JpaRepository<Enrollment, EnrollmentId> getRepository() {
        return enrollmentRepository;
    }

    @Override
    public void enrollmentValidator(Integer personId, Integer courseId) {
        if ( ! enrollmentRepository.isPersonEnrolledInCourse(personId, courseId) )
            throw new BusinessRuleViolation("Person with id "
                    + personId
                    + " is not at all enrolled in course with id "
                    + courseId
                    + ".");
    }

    @Override
    public void activeEnrollmentValidator(Integer personId, Integer courseId) {
        if ( ! enrollmentRepository.isPersonActiveEnrolledInCourse(personId, courseId) )
            throw new BusinessRuleViolation("Person with id "
                    + personId
                    + " is not enrolled in course with id "
                    + courseId
                    + ".");
    }

}
