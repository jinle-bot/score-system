package cz.spgsasoskladno.infosystem.mapper;

import cz.spgsasoskladno.infosystem.domain.ActivityGrade;
import cz.spgsasoskladno.infosystem.dto.activity.ActivitySummaryDto;
import cz.spgsasoskladno.infosystem.dto.grade.ActivityGradeItemResponseDto;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ActivityGradeMapper {

    private final ActivityMapper activityMapper;

    public ActivityGradeMapper(ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    @Nullable
    public ActivityGradeItemResponseDto toDto(@Nullable ActivityGrade entity) {
        if (entity == null) return null;

        final String pointsStr = entity.getPoints() != null
                ? entity.getPoints().toString()
                : (entity.getGradeStatus() != null ? entity.getGradeStatus().name() : null);

        final String markStr = entity.getMark() != null
                ? entity.getMark().toString()
                : (entity.getGradeStatus() != null ? entity.getGradeStatus().name() : null);

        final ActivitySummaryDto activitySummaryDto =
                activityMapper.toSummaryDto(entity.getActivity());

        return new ActivityGradeItemResponseDto(
                activitySummaryDto,
                pointsStr,
                markStr,
                entity.getAddPoints(),     // Integer
                entity.getRepairs(),
                entity.getActivityDate(),  // LocalDate
                entity.getCreatedAt()      // LocalDateTime
        );
    }

}
