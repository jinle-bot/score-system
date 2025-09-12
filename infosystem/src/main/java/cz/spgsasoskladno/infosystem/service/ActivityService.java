package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService extends CrudService<Activity, Integer> {
    List<Activity> getActivitiesBySubjectId(Integer subjectId);
    Activity getActivityByActivityIdAndSubjectId(Integer activityId, Integer subjectId);
}
