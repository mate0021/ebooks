package rnd.mate00.ebooks.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.ReadingProgress;
import rnd.mate00.ebooks.model.ReadingProgressKey;

import java.util.Date;
import java.util.List;

/**
 * Created by mate00 on 28.01.18.
 */
public interface ReadingProgressRepository extends CrudRepository<ReadingProgress, ReadingProgressKey> {

    @Modifying
    @Query("UPDATE reading_progress SET READING_END = :endDate WHERE KEY_BOOK = :keyBook AND KEY_READER = :keyReader")
    int setEndDateForReadingProgress(@Param("endDate") Date endDate,
                                     @Param("keyBook") Book book,
                                     @Param("keyReader") Reader reader);

    @Query("select key, start from reading_progress where key_reader = :keyReader and reading_end is null")
    List<ReadingProgressKey> findStartedByKeyReader(@Param("keyReader") Reader reader);
}
