package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Subject;

import java.util.List;

public interface SubjectService extends CrudService<Subject, Integer> {

    Subject addStudentIntoSubject(Integer studentId, Integer subjectId);
    List<Subject> getSubjectsByStudentId(Integer studentId);

}
