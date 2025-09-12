package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Course;
import cz.spgsasoskladno.infosystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CourseServiceImpl extends CrudServiceImpl<Course, Integer> implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    protected JpaRepository<Course, Integer> getRepository() {
        return courseRepository;
    }

}
