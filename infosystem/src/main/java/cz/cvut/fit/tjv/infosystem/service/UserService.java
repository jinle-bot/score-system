package cz.cvut.fit.tjv.infosystem.service;

import cz.cvut.fit.tjv.infosystem.domain.User;
import cz.cvut.fit.tjv.infosystem.dto.RegisterRequest;
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
