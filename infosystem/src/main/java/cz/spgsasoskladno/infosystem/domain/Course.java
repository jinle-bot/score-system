package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "course")
public class Course implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL in Postgres
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "concrete_subject_semester_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_course_concrete_subject_semester")
    )
    private ConcreteSubjectSemester concreteSubjectSemester;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Filled by DB default (NOW()); keep read-only from JPA perspective
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "max_points")
    private Integer maxPoints;

    @Column(name = "min_points")
    private Integer minPoints;

    /** Required by JPA. */
    protected Course() {}

    /** Constructor to enforce valid state. */
    public Course(ConcreteSubjectSemester concreteSubjectSemester, String name) {
        this.concreteSubjectSemester = concreteSubjectSemester;
        this.name = name;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public ConcreteSubjectSemester getConcreteSubjectSemester() { return concreteSubjectSemester; }
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getMaxPoints() { return maxPoints; }
    public Integer getMinPoints() { return minPoints; }


    // --- setters for mutable attributes ---

    public void setConcreteSubjectSemester(ConcreteSubjectSemester concreteSubjectSemester) {
        this.concreteSubjectSemester = concreteSubjectSemester;
    }
    public void setName(String name) { this.name = name; }
    public void setMaxPoints(Integer maxPoints) { this.maxPoints = maxPoints; }
    public void setMinPoints(Integer minPoints) { this.minPoints = minPoints; }
}
