package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.sec.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleName(String name);
}
