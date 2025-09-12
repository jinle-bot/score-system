package cz.spgsasoskladno.infosystem_client.dto;

public class UserSession {

    private Integer Id;
    private String username;
    private String userType;
    private String token;

    public UserSession() {}

    public UserSession(Integer id, String username, String userType, String token) {
        Id = id;
        this.username = username;
        this.userType = userType;
        this.token = token;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
