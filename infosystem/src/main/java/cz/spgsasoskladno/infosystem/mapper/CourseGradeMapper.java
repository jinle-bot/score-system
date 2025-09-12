package cz.spgsasoskladno.infosystem.mapper;

import cz.spgsasoskladno.infosystem.domain.CourseGrade;
import cz.spgsasoskladno.infosystem.dto.grade.CourseGradeResponseDto;
import org.springframework.stereotype.Component;


@Component
public class CourseGradeMapper {

    public CourseGradeResponseDto toDto(CourseGrade entity) {
        if (entity == null) {
            return null;
        }
        return new CourseGradeResponseDto(
                entity.getPoints(),
                entity.getMark()
        );
    }

}
