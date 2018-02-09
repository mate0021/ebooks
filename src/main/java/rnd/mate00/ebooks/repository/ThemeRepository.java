package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.model.Theme;

/**
 * Created by mate00 on 23.01.18.
 */
public interface ThemeRepository extends CrudRepository<Theme, Integer> {
}
