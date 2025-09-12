package cz.spgsasoskladno.infosystem_client.dto;

import java.time.LocalDate;

public class ActivityDTO {

    private Integer Id;
    private String name;
    private String description;
    private String minimum;
    private String maximum;
    private boolean mandatory;
    private String priority;
    private SubjectDTO subject;
    private LocalDate date;

    public ActivityDTO() {}

    public ActivityDTO(Integer id,
                       String name,
                       String description,
                       String minimum,
                       String maximum,
                       boolean mandatory,
                       String priority,
                       SubjectDTO subject,
                       LocalDate date) {

        Id = id;
        this.name = name;
        this.description = description;
        this.minimum = minimum;
        this.maximum = maximum;
        this.mandatory = mandatory;
        this.priority = priority;
        this.subject = subject;
        this.date = date;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
