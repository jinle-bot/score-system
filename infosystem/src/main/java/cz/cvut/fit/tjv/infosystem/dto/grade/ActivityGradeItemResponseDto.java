package cz.cvut.fit.tjv.infosystem.dto.grade;

import cz.cvut.fit.tjv.infosystem.dto.activity.ActivitySummaryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ActivityGradeItemResponseDto(
        ActivitySummaryDto activitySummaryDto,
        String points,
        String mark,
        Integer addPoints,
        Integer repaired,
        LocalDate activityDate,
        LocalDateTime createdAt) {
}
