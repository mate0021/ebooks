package rnd.mate00.ebooks.repository;

import org.springframework.data.repository.CrudRepository;
import rnd.mate00.ebooks.model.Shopping;
import rnd.mate00.ebooks.model.ShoppingKey;

/**
 * Created by mate00 on 12.02.18.
 */
public interface ShoppingRepository extends CrudRepository<Shopping, ShoppingKey> {
}
