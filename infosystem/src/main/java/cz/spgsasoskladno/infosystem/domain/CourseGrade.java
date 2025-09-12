package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "course_grade",
        indexes = {
                @Index(name = "idx_course_grade_person", columnList = "course_id,person_id")
        }
)
// Mirrors: CHECK (valid_to IS NULL OR valid_to > valid_from)
@org.hibernate.annotations.Check(
        name = "chk_course_valid_range",
        constraints = "(valid_to IS NULL OR valid_to > valid_from)"
)
@Immutable
public class CourseGrade implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL in Postgres
    private Integer id;

    // FK (course_id, person_id) → enrollment(course_id, person_id)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumns(
            value = {
                    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false,
                            foreignKey = @ForeignKey(name = "fk_course_grade_enrollment")),
                    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false,
                            foreignKey = @ForeignKey(name = "fk_course_grade_enrollment"))
            }
    )
    private Enrollment enrollment;

    @Column(name = "points")
    private Integer points;

    @Column(name = "mark")
    private Integer mark;

    // DB default NOW(); keep read-only on JPA side
    @Column(name = "valid_from", nullable = false, insertable = false, updatable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    /** Required by JPA */
    protected CourseGrade() {}


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public Enrollment getEnrollment() { return enrollment; }
    public Integer getPoints() { return points; }
    public Integer getMark() { return mark; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public LocalDateTime getValidTo() { return validTo; }


    // Convenience accessors (not persisted)
    @Transient
    public Person getPerson() { return enrollment != null ? enrollment.getPerson() : null; }

    @Transient
    public Course getCourse() { return enrollment != null ? enrollment.getCourse() : null; }
}
