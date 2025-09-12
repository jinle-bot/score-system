package cz.spgsasoskladno.infosystem.service;

import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.dto.RegisterRequest;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

@Service
public interface UserService extends CrudService<User, Integer> {
    Collection<User> readAllStudents();
    User register(RegisterRequest request);
    List<User> createUsers(List<User> users);
    User readByUsername(String username);
}
