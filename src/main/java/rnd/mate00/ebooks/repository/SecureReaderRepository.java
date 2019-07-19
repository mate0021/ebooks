package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;

public interface SecureReaderRepository extends CrudRepository<User, Integer> {
}
