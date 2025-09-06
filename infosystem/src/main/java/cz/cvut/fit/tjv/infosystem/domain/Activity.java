package cz.cvut.fit.tjv.infosystem.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Activity implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Integer id;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private String name;

    @Column
    @JsonView(View.Summary.class)
    private String description;

    @Column
    @JsonView(View.Summary.class)
    private LocalDate date;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private boolean mandatory;

    @Column(nullable = false)
    @JsonView(View.Summary.class)
    private Integer priority;

    @Column
    @JsonView(View.Summary.class)
    private Integer minimum;

    @Column
    @JsonView(View.Summary.class)
    private Integer maximum;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView(View.Summary.class)
    private Subject subject;

    // constructors
    public Activity() {}

    public Activity(Subject subject) {
        this.subject = subject;
    }

    // methods
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
