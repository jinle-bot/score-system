package cz.cvut.fit.tjv.infosystem.restController;

public class ApiPaths {

    public static final String ROOT = "/api/v1";
    public static final String BASE_AUTH = ROOT + "/auth";
    public static final String BASE_USERS = ROOT + "/users";
    public static final String BASE_SUBJECTS = ROOT + "/subjects";
    public static final String BASE_ACTIVITIES = BASE_SUBJECTS + "/{subjectId}/activities";
    public static final String BASE_GRADES = BASE_SUBJECTS + "/{subjectId}/{studentId}/grades";

}
