package cz.spgsasoskladno.infosystem.repository;

import cz.spgsasoskladno.infosystem.domain.User;
import cz.spgsasoskladno.infosystem.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Collection<User> findAllByUserType(UserType userType);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
