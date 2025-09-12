package cz.spgsasoskladno.infosystem.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.spgsasoskladno.infosystem.domain.Activity;
import cz.spgsasoskladno.infosystem.domain.View;

import java.time.LocalDate;


public class GradeDTO {

    @JsonView(View.Summary.class)
    private Integer id;

    @JsonView(View.Summary.class)
    private String username;

    @JsonView(View.Detailed.class)
    private Activity activity;

    @JsonView(View.Summary.class)
    private Integer score;

    @JsonView(View.Summary.class)
    private LocalDate date;

    //constructors
    public GradeDTO() {}

    public GradeDTO(Integer id, String studentUsername, Activity activity, Integer score, LocalDate date) {
        this.id = id;
        this.username = studentUsername;
        this.activity = activity;
        this.score = score;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
