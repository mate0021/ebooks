package rnd.mate00.ebooks.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shopping;
import rnd.mate00.ebooks.model.ShoppingKey;

import java.util.List;

/**
 * Created by mate00 on 12.02.18.
 */
public interface ShoppingRepository extends CrudRepository<Shopping, ShoppingKey> {

    @Query("select s from shopping s where key_book = :keyBook and key_reader = :keyReader order by buy_date desc")
    List<Shopping> findLatestShoppingBookByReader(@Param("keyBook") Book book, @Param("keyReader") Reader reader);
}
