package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class EnrollmentId implements Serializable {

    private Integer personId;
    private Integer courseId;

    protected EnrollmentId() {}

    public EnrollmentId(Integer personId, Integer courseId) {
        this.personId = personId;
        this.courseId = courseId;
    }


    // --- getters ---

    public Integer getPersonId() { return personId; }
    public Integer getCourseId() { return courseId; }


    // --- equals & hashCode are required for composite key ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, courseId);
    }
}
