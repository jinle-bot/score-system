package cz.spgsasoskladno.infosystem.dto.grade;

import cz.spgsasoskladno.infosystem.dto.course.CourseHeaderDto;
import java.util.List;

public record CourseGradesResponseDto(
        CourseHeaderDto course,
        List<ActivityGradeItemResponseDto> items,
        CourseGradeResponseDto summary
) {}