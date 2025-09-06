package cz.cvut.fit.tjv.infosystem_client.dto;


public class SubjectDTO {

    private Integer Id;
    private String name;
    private String schoolYear;
    private Integer semester;

    public SubjectDTO() {}
    public SubjectDTO(Integer id, String name, String schoolYear, Integer semester) {
        this.Id = id;
        this.name = name;
        this.schoolYear = schoolYear;
        this.semester = semester;
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

}
