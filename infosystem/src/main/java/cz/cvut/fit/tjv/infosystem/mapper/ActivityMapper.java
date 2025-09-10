package cz.cvut.fit.tjv.infosystem.mapper;

import cz.cvut.fit.tjv.infosystem.domain.ActivityNew;
import cz.cvut.fit.tjv.infosystem.dto.activity.ActivitySummaryDto;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;


@Component
public class ActivityMapper {

    @Nullable
    public ActivitySummaryDto toSummaryDto(@Nullable ActivityNew activity) {
        if (activity == null) return null;
        return new ActivitySummaryDto(
                activity.getName(),
                activity.getDescription(),
                activity.getMinPoints(),
                activity.getMaxPoints(),
                activity.getDueDate()
        );
    }

}
