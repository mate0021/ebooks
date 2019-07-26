package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.sec.SecureReader;

public interface SecureReaderRepository extends CrudRepository<SecureReader, Integer> {
}
