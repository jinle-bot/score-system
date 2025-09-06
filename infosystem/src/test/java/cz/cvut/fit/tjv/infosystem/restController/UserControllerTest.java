package cz.cvut.fit.tjv.infosystem.restController;

import cz.cvut.fit.tjv.infosystem.domain.User;
import cz.cvut.fit.tjv.infosystem.domain.UserType;
import cz.cvut.fit.tjv.infosystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldReturnListOfUsers() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("John");
        user1.setUserType(UserType.TEACHER);
        User user2 = new User();
        user2.setId(2);
        user1.setUsername("Adela");
        user1.setUserType(UserType.STUDENT);
        User user3 = new User();
        user3.setId(3);
        user3.setUsername("Pavla");
        user3.setUserType(UserType.STUDENT);
        List<User> users = Arrays.asList(
                user1,user2,user3
                );
        when(userService.readAll()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    void shouldCreateUser() throws Exception {

        User savedUser = new User(2, "Jane", "heslo", "jane@example.com", UserType.STUDENT);
        when(userService.create(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Jane\", \"password\": \"heslo\", \"email\": \"jane@example.com\", " +
                                 "\"userType\" : \"STUDENT\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.username").value(savedUser.getUsername()))
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));

    }


}