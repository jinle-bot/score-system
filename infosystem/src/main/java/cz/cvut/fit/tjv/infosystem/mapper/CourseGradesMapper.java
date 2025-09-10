package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.ActivityGrade;
import cz.cvut.fit.tjv.infosystem.domain.Course;
import cz.cvut.fit.tjv.infosystem.domain.CourseGrade;
import cz.cvut.fit.tjv.infosystem.dto.course.CourseHeaderDto;
import cz.cvut.fit.tjv.infosystem.dto.grade.ActivityGradeItemResponseDto;
import cz.cvut.fit.tjv.infosystem.dto.grade.CourseGradeResponseDto;
import cz.cvut.fit.tjv.infosystem.dto.grade.CourseGradesResponseDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class CourseGradesMapper {

    private final ActivityGradeMapper activityGradeMapper;
    private final CourseGradeMapper courseGradeMapper;
    private final CourseMapper courseMapper;

    public CourseGradesMapper(ActivityGradeMapper activityGradeMapper,
                              CourseGradeMapper courseGradeMapper,
                              CourseMapper courseMapper) {
        this.activityGradeMapper = activityGradeMapper;
        this.courseGradeMapper = courseGradeMapper;
        this.courseMapper = courseMapper;
    }

    public CourseGradesResponseDto toDto(Course course,
                                         List<ActivityGrade> activityGrades,
                                         CourseGrade summaryGrade) {
        List<ActivityGradeItemResponseDto> items = activityGrades.stream()
                .map(activityGradeMapper::toDto)
                .collect(Collectors.toList());

        CourseGradeResponseDto summary = courseGradeMapper.toDto(summaryGrade);

        CourseHeaderDto header = courseMapper.toHeaderDto(course);

        return new CourseGradesResponseDto(
                header,
                items,
                summary
        );
    }

}
