package cz.spgsasoskladno.infosystem.dto.grade;

import cz.spgsasoskladno.infosystem.dto.activity.ActivitySummaryDto;

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
