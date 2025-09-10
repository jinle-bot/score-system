package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.Course;
import cz.cvut.fit.tjv.infosystem.dto.course.CourseHeaderDto;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class CourseMapper {

    @Nullable
    public CourseHeaderDto toHeaderDto(@Nullable Course entity) {
        if (entity == null) {
            return null;
        }
        return new CourseHeaderDto(
                entity.getName()
        );
    }

}
