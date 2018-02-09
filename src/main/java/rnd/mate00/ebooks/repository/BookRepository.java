package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.model.Book;

import java.util.Optional;

/**
 * Created by mate00 on 23.01.18.
 */
public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);
}
