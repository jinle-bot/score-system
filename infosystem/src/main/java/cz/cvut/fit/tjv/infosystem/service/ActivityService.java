package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService extends CrudService<Activity, Integer> {
    List<Activity> getActivitiesBySubjectId(Integer subjectId);
    Activity getActivityByActivityIdAndSubjectId(Integer activityId, Integer subjectId);
}
