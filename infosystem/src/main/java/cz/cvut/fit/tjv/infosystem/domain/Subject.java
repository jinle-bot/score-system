package cz.cvut.fit.tjv.infosystem.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table( uniqueConstraints = @UniqueConstraint( columnNames = {"name", "school_year", "semester"} ) )
public class Subject implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Integer id;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private String name;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private String schoolYear;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private Integer semester;

    @ManyToMany
    @JoinTable( name = "student_subject",
                joinColumns = @JoinColumn(name = "subject_id"),
                inverseJoinColumns = @JoinColumn(name = "student_id"))
    @OrderBy("username ASC")
    @JsonView(View.Detailed.class)
    private Set<User> students = new HashSet<>();

    //@OneToMany (mappedBy = "subject")
    //private List<Activity> activities;


    @Override
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Set<User> getStudents() {
        return students;
    }
}
