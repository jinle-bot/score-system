package cz.spgsasoskladno.infosystem.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "person",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class Person implements EntityWithID<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SERIAL
    private Integer id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, columnDefinition = "text")
    private String passwordHash;

    // Filled by DB (DEFAULT NOW()); read-only from the point of view of JPA.
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "user_role")
    private UserType role;

    /** Required by JPA (Hibernate uses the no-args constructor via reflection). */
    protected Person() {}

    /** Convenience constructor to ensure a valid state at creation time. */
    public Person(String username, String email, String passwordHash, UserType role) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }


    // --- getters ---

    @Override
    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public UserType getRole() { return role; }


    // --- setters ---

    public void setUsername(String username) { this.username = username; }

    public void setEmail(String email) { this.email = email; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public void setRole(UserType role) { this.role = role; }

}
