package cz.spgsasoskladno.infosystem_client.dto;

import java.time.LocalDate;


public class GradeDTO {

    private Integer id;
    private String username;
    private ActivityDTO activity;
    private Integer score;
    private LocalDate date;

    //constructors
    public GradeDTO() {}

    public GradeDTO(String studentUsername, ActivityDTO activity, Integer score, LocalDate date) {
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

    public ActivityDTO getActivity() {
        return activity;
    }

    public void setActivity(ActivityDTO activity) {
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
