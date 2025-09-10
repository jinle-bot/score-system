package cz.cvut.fit.tjv.infosystem.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "enrollment")
public class Enrollment implements EntityWithID<EnrollmentId> {

    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("personId") // maps PK part
    @JoinColumn(
            name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_enrollment_person")
    )
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("courseId") // maps PK part
    @JoinColumn(
            name = "course_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_enrollment_course")
    )
    private Course course;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    /** Required by JPA */
    protected Enrollment() {}

    public Enrollment(Person person, Course course) {
        this.person = person;
        this.course = course;
        this.id = new EnrollmentId(person.getId(), course.getId());
    }


    // --- getters ---

    @Override
    public EnrollmentId getId() { return id; }
    public Person getPerson() { return person; }
    public Course getCourse() { return course; }
    public boolean isActive() { return active; }


    // --- setters ---

    public void setActive(boolean active) { this.active = active; }
}
