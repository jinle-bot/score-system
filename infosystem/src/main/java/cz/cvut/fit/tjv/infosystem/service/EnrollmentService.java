package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Enrollment;
import cz.cvut.fit.tjv.infosystem.domain.EnrollmentId;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService extends CrudService<Enrollment, EnrollmentId> {

    public void enrollmentValidator(Integer personId, Integer courseId);
    public void activeEnrollmentValidator(Integer personId, Integer courseId);

}
