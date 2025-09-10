package cz.cvut.fit.tjv.infosystem.domain;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "concrete_subject_semester")
public class ConcreteSubjectSemester implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL in Postgres
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_concrete_subject_semester_subject")
    )
    private SubjectNew subject;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /** Required by JPA (Hibernate uses reflection). */
    protected ConcreteSubjectSemester() {}

    /** Constructor to enforce valid state. */
    public ConcreteSubjectSemester(SubjectNew subject, LocalDate dateFrom, LocalDate dateTo) {
        this.subject = subject;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public SubjectNew getSubject() { return subject; }
    public LocalDate getDateFrom() { return dateFrom; }
    public LocalDate getDateTo() { return dateTo; }


    // --- setters for mutable attributes (skip id) ---

    public void setSubject(SubjectNew subject) { this.subject = subject; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }
}
