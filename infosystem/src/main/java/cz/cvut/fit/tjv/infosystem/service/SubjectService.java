package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Subject;

import java.util.Collection;
import java.util.List;

public interface SubjectService extends CrudService<Subject, Integer> {

    Subject addStudentIntoSubject(Integer studentId, Integer subjectId);
    List<Subject> getSubjectsByStudentId(Integer studentId);

}
