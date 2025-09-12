package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.domain.UserType;
import cz.spgsasoskladno.infosystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class)))
                .thenAnswer(invocation -> "encoded" + invocation.getArgument(0).toString());


    }

    @Test
    void testCreateUserSuccess() {
        // Case 1: User with email, without id (id will be generated)
        User userWithEmail = new User();
        userWithEmail.setUsername("user1");
        userWithEmail.setPassword("password1");
        userWithEmail.setUserType(UserType.STUDENT);
        userWithEmail.setEmail("user1@example.com");

        // When checking for duplicate username, return false
        when(userRepository.existsByUsername("user1")).thenReturn(false);
        // Simulate saving: if id is null, assign a generated id (e.g., 1L)
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            if (u.getId() == null) {
                u.setId(1);
            }
            return u;
        });

        User createdUser = userService.create(userWithEmail);
        assertNotNull(createdUser.getId());
        assertEquals("user1", createdUser.getUsername());

        // Case 2: User without email
        User userWithoutEmail = new User();
        userWithoutEmail.setUsername("user2");
        userWithoutEmail.setPassword("password2");
        userWithoutEmail.setUserType(UserType.STUDENT);
        userWithoutEmail.setEmail(null);

        when(userRepository.existsByUsername("user2")).thenReturn(false);
        User createdUser2 = userService.create(userWithoutEmail);
        assertNotNull(createdUser2.getId());
        assertEquals("user2", createdUser2.getUsername());

        // Case 3: User with an id provided that is not in the database
        User userWithId = new User();
        userWithId.setId(55);
        userWithId.setUsername("user3");
        userWithId.setPassword("password3");
        userWithId.setUserType(UserType.STUDENT);
        userWithId.setEmail("user3@example.com");

        when(userRepository.existsByUsername("user3")).thenReturn(false);
        // Simulate that the id 999 does not exist yet
        when(userRepository.existsById(55)).thenReturn(false);
        // For users with an id provided, you might choose to simply save the object as is
        when(userRepository.save(userWithId)).thenReturn(userWithId);

        User createdUser3 = userService.create(userWithId);
        assertEquals(55, createdUser3.getId());

    }


    @Test
    void testCreateUserFailsDueToExistingUsername() {
        User duplicateUser = new User();
        duplicateUser.setUsername("existingUser");
        duplicateUser.setPassword("password");
        duplicateUser.setUserType(UserType.STUDENT);
        duplicateUser.setEmail("existing@example.com");

        // Simulate that the username is already present
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        Exception exception = assertThrows(EntityExistsException.class, () -> {
            userService.create(duplicateUser);
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void testCreateUserFailsDueToExistingId() {
        User duplicateIdUser = new User();
        duplicateIdUser.setId(1);
        duplicateIdUser.setUsername("user4");
        duplicateIdUser.setPassword("password4");
        duplicateIdUser.setUserType(UserType.TEACHER);
        duplicateIdUser.setEmail("user4@example.com");

        when(userRepository.existsByUsername("user4")).thenReturn(false);
        // Simulate that id 1L is already in the database
        when(userRepository.existsById(1)).thenReturn(true);

        Exception exception = assertThrows(EntityExistsException.class, () -> {
            userService.create(duplicateIdUser);
        });

        assertEquals("Entity by ID already exists", exception.getMessage());
    }

    @Test
    void testCreateUserFailsDueToEmptyUsername() {
        User user1 = new User();
        user1.setUsername(""); // empty username
        user1.setPassword("password");
        user1.setUserType(UserType.STUDENT);
        user1.setEmail("user@example.com");

        User user2 = new User();
        user2.setPassword("password");
        user2.setUserType(UserType.STUDENT);
        user2.setEmail("user@example.com");

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user1);
        });

        assertEquals("Username or password or user type cannot be empty", exception1.getMessage());


        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user2);
        });

        assertEquals("Username or password or user type cannot be empty", exception2.getMessage());
    }

    @Test
    void testCreateUserFailsDueToEmptyUserType() {
        User user = new User();
        user.setUsername("user5");
        user.setPassword("password5");
        user.setEmail("user5@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });

        assertEquals("Username or password or user type cannot be empty", exception.getMessage());
    }

    @Test
    void testCreateUserFailsDueToEmptyPassword() {
        User user = new User();
        user.setUsername("user6");
        user.setPassword(""); // empty password
        user.setUserType(UserType.STUDENT);
        user.setEmail("user6@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(user);
        });

        assertEquals("Username or password or user type cannot be empty", exception.getMessage());
    }


}