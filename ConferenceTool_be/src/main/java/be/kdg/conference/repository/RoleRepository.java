package be.kdg.conference.repository;

import be.kdg.conference.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
