package be.kdg.conference.repository;

import be.kdg.conference.model.account.Roles;
import be.kdg.conference.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByRolesRoleName (Roles roleName);
}
