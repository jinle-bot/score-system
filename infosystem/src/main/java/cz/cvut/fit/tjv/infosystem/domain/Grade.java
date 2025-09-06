package cz.cvut.fit.tjv.infosystem.domain;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tjv.infosystem.dto.GradeDTO;
import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = {"student_id", "activity_id"} ) )
public class Grade implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonView(View.Summary.class)
    private User student;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @JsonView(View.Summary.class)
    private Activity activity;

    @Column
    @JsonView(View.Summary.class)
    private Integer score;

    @Column
    @JsonView(View.Summary.class)
    private LocalDate date;

    //constructors
    public Grade() {}

    public Grade(User student, Activity activity) {
        this.student = student;
        this.activity = activity;
        this.score = 0;
        this.date = LocalDate.now();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public GradeDTO toDTO() {
        GradeDTO dto = new GradeDTO();
        dto.setId(id);
        dto.setUsername(student.getUsername());
        dto.setActivity(activity);
        dto.setScore(score);
        dto.setDate(date);
        return dto;
    }



}
