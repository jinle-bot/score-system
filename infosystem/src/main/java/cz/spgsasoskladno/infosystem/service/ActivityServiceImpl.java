package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.Activity;
import cz.spgsasoskladno.infosystem.domain.Subject;
import cz.spgsasoskladno.infosystem.repository.ActivityRepository;
import cz.spgsasoskladno.infosystem.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class ActivityServiceImpl extends CrudServiceImpl<Activity, Integer> implements ActivityService {

    private final ActivityRepository activityRepository;
    private final SubjectRepository subjectRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, SubjectRepository subjectRepository) {
        this.activityRepository = activityRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    protected Activity checkAndPut (Activity activity) {
        if ( activity.getSubject() == null || activity.getSubject().getId() == null )
        {throw new IllegalArgumentException("Activity must have any Subject");}
        Optional<Subject> optSubject = subjectRepository.findById(activity.getSubject().getId());
        if ( optSubject.isEmpty() )
        {throw new IllegalArgumentException("Invalid Subject ID");}
        activity.setSubject( optSubject.get() );
        return activityRepository.save(activity);
    }

    @Override
    protected JpaRepository<Activity, Integer> getRepository() {
        return activityRepository;
    }

    @Override
    public List<Activity> getActivitiesBySubjectId(Integer subjectId) {
        if ( ! subjectRepository.existsById(subjectId) )
            throw new IllegalArgumentException("Invalid Subject ID ");
        return activityRepository.findAllBySubjectId(subjectId);
    }

    @Override
    public Activity getActivityByActivityIdAndSubjectId(Integer activityId, Integer subjectId) {
        Optional<Subject> optSubject = subjectRepository.findById(subjectId);
        if ( optSubject.isEmpty() )
            throw new IllegalArgumentException("Invalid Subject ID");
        Subject subject = optSubject.get();
        Optional<Activity> optActivity = activityRepository.findById(activityId);
        if ( optActivity.isEmpty() )
            throw new IllegalArgumentException("Invalid Activity ID");
        Activity activity = optActivity.get();
        if ( ! activity.getSubject().getId().equals(subject.getId()) )
            throw new EntityNotFoundException("Found Activity is in the scope of Subject ID" + activity.getSubject().getId() );
        return activity;
    }
}
