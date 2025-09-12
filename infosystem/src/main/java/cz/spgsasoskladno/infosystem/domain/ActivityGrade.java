package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(
        name = "activity_grade"
)
@org.hibernate.annotations.Check(
        name = "chk_activity_valid_range",
        constraints = "(valid_to IS NULL OR valid_to > valid_from)"
)
@org.hibernate.annotations.Check(
        name = "chk_activity_points_or_status",
        constraints = "(points IS NOT NULL OR grade_status IS NOT NULL)"
)
public class ActivityGrade implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "activity_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_activity_grade_activity")
    )
    private ActivityNew activity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_activity_grade_person")
    )
    private Person person;

    @Column(name = "add_points", nullable = false)
    private Integer addPoints = 0;

    @Column(name = "points")
    private Integer points;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "repairs", nullable = false)
    private Integer repairs = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_status", columnDefinition = "grade_status")
    private GradeStatus gradeStatus;

    @Column(name = "activity_date")
    private LocalDate activityDate;

    // controlled by DB default
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "valid_from", nullable = false, insertable = false, updatable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    /** Required by JPA */
    protected ActivityGrade() {}

    public ActivityGrade(ActivityNew activity, Person person, Integer points) {
        this.activity = activity;
        this.person = person;
        this.points = points;
    }

    public ActivityGrade(ActivityNew activity, Person person, Integer points,  LocalDate activityDate) {
        this.activity = activity;
        this.person = person;
        this.points = points;
        this.activityDate = activityDate;
    }

    public ActivityGrade(ActivityNew activity, Person person, GradeStatus gradeStatus) {
        this.activity = activity;
        this.person = person;
        this.gradeStatus = gradeStatus;
    }

    public ActivityGrade(ActivityNew activity, Person person, GradeStatus gradeStatus, LocalDate activityDate) {
        this.activity = activity;
        this.person = person;
        this.gradeStatus = gradeStatus;
        this.activityDate = activityDate;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public ActivityNew getActivity() { return activity; }
    public Person getPerson() { return person; }
    public Integer getAddPoints() { return addPoints; }
    public Integer getPoints() { return points; }
    public Integer getMark() { return mark; }
    public Integer getRepairs() { return repairs; }
    public GradeStatus getGradeStatus() { return gradeStatus; }
    public LocalDate getActivityDate() { return activityDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public LocalDateTime getValidTo() { return validTo; }


    // --- setters for mutable attributes ---

    public void setActivity(ActivityNew activity) { this.activity = activity; }
    public void setPerson(Person person) { this.person = person; }
    public void setAddPoints(Integer addPoints) { this.addPoints = addPoints; }
    public void setPoints(Integer points) { this.points = points; }
    public void setMark(Integer mark) { this.mark = mark; }
    public void setRepairs(Integer repairs) { this.repairs = repairs; }
    public void setGradeStatus(GradeStatus gradeStatus) { this.gradeStatus = gradeStatus; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }

}
