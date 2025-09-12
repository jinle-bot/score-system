package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Enrollment;
import cz.spgsasoskladno.infosystem.domain.EnrollmentId;
import org.springframework.stereotype.Service;

@Service
public interface EnrollmentService extends CrudService<Enrollment, EnrollmentId> {

    public void enrollmentValidator(Integer personId, Integer courseId);
    public void activeEnrollmentValidator(Integer personId, Integer courseId);

}
