package cz.cvut.fit.tjv.infosystem_client.dto;

public class UserDTO {

    private Integer Id;
    private String username;
    private String email;

    public UserDTO() {}
    public UserDTO(Integer id, String username, String email) {
        Id = id;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
