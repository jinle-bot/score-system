package cz.cvut.fit.tjv.infosystem.dto.grade;

import cz.cvut.fit.tjv.infosystem.dto.course.CourseHeaderDto;
import java.util.List;

public record CourseGradesResponseDto(
        CourseHeaderDto course,
        List<ActivityGradeItemResponseDto> items,
        CourseGradeResponseDto summary
) {}