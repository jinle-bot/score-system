package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "activity")
public class ActivityNew implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL in Postgres
    private Integer id;

    @Column(name = "conseq_number", nullable = false)
    private Integer conseqNumber;

    @Column(name = "mandatory", nullable = false)
    private boolean mandatory = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_activity_course")
    )
    private Course course;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "max_points")
    private Integer maxPoints;

    @Column(name = "min_points")
    private Integer minPoints;

    // DB default NOW(); keep read-only from JPA side
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "due_date")
    private LocalDate dueDate;

    /** Required by JPA */
    protected ActivityNew() {}

    /** Constructor for required fields */
    public ActivityNew(Integer conseqNumber, Course course, String name) {
        this.conseqNumber = conseqNumber;
        this.course = course;
        this.name = name;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public Integer getConseqNumber() { return conseqNumber; }
    public boolean isMandatory() { return mandatory; }
    public Course getCourse() { return course; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getMaxPoints() { return maxPoints; }
    public Integer getMinPoints() { return minPoints; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDate getDueDate() { return dueDate; }


    // --- setters for mutable attributes ---

    public void setConseqNumber(Integer conseqNumber) { this.conseqNumber = conseqNumber; }
    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }
    public void setCourse(Course course) { this.course = course; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setMaxPoints(Integer maxPoints) { this.maxPoints = maxPoints; }
    public void setMinPoints(Integer minPoints) { this.minPoints = minPoints; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

}
