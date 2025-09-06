package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.User;
import cz.cvut.fit.tjv.infosystem.domain.UserType;
import cz.cvut.fit.tjv.infosystem.dto.RegisterRequest;
import cz.cvut.fit.tjv.infosystem.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl extends CrudServiceImpl<User, Integer>
        implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected User checkAndPut (User user) {
        if ( user.getUsername() == null || user.getUsername().isEmpty() ||
             user.getPassword() == null || user.getPassword().isEmpty() || user.getUserType() == null ) {
            throw new IllegalArgumentException("Username or password or user type cannot be empty");
        }
        if ( user.getEmail() != null &&
                ! user.getEmail().isEmpty() &&
                userRepository.existsByEmail(user.getEmail()) ) {
            throw new EntityExistsException("Email already exists");
        }
        if ( userRepository.existsByUsername(user.getUsername()) ) {
            throw new EntityExistsException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return getRepository().save(user);
    }

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return userRepository;
    }

    @Override
    public Collection<User> readAllStudents() {
        return userRepository.findAllByUserType(UserType.STUDENT);
    }

    @Override
    public User register(RegisterRequest request) {
        if ( request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty() ) {
            throw new IllegalArgumentException("Username or password cannot be empty");
        }
        if ( request.getEmail() != null &&
                ! request.getEmail().isEmpty() &&
                userRepository.existsByEmail(request.getEmail()) ) {
            throw new EntityExistsException("Email already exists");
        }
        if ( userRepository.existsByUsername(request.getUsername()) ) {
            throw new EntityExistsException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), hashedPassword, request.getEmail(), UserType.STUDENT);
        return getRepository().save(user);
    }

    @Override
    @Transactional
    public List<User> createUsers(List<User> users) {
        users.forEach(user -> {
            if (user.getId() != null) {
                throw new IllegalArgumentException("User ID must be null for new users");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        });
        return userRepository.saveAll(users);
    }

    public User readByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Username not found");
        }
        return user.get();
    }

}
