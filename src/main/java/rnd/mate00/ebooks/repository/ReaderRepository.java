package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.model.Reader;

import java.util.Optional;

/**
 * Created by mate00 on 23.01.18.
 */
public interface ReaderRepository extends CrudRepository<Reader, Integer> {

    Optional<Reader> findByName(String name);
}
