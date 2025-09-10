package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.ActivityGrade;
import cz.cvut.fit.tjv.infosystem.dto.activity.ActivitySummaryDto;
import cz.cvut.fit.tjv.infosystem.dto.grade.ActivityGradeItemResponseDto;
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
