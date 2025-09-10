package cz.cvut.fit.tjv.infosystem.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "subject",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_subject", columnNames = {"name", "year", "semester"})
        }
)
public class SubjectNew implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL in Postgres
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    // Filled by DB default (NOW()); keep read-only from JPA perspective
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    /** Required by JPA (Hibernate uses the no-args constructor via reflection). */
    protected SubjectNew() {}

    /** Convenience constructor to ensure a valid state at creation time. */
    public SubjectNew(String name, Integer year, Integer semester) {
        this.name = name;
        this.year = year;
        this.semester = semester;
    }

    /** Optional description at creation. */
    public SubjectNew(String name, Integer year, Integer semester, String description) {
        this(name, year, semester);
        this.description = description;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getYear() { return year; }
    public Integer getSemester() { return semester; }


    // --- setters for mutable attributes (omit setters for id and createdAt) ---

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setYear(Integer year) { this.year = year; }

    public void setSemester(Integer semester) { this.semester = semester; }
}
