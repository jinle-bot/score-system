package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.CourseGrade;
import cz.cvut.fit.tjv.infosystem.dto.grade.CourseGradeResponseDto;
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
