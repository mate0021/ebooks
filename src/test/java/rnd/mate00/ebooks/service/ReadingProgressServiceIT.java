package rnd.mate00.ebooks.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rnd.mate00.ebooks.model.*;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ReadingProgressRepository;

import java.time.LocalDate;

/**
 * Created by mate00 on 28.01.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReadingProgressServiceIT {

    @Autowired
    private ReadingProgressService readingProgressService;

    @Autowired
    private ReadingProgressRepository readingProgressRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    private Reader reader;

    private Book book;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        reader = readerRepository.save(new Reader(1, "reader1"));
        book = bookRepository.save(new Book(1, "title", "author", 3456, new Theme("romance")));
    }


    @Test
    public void startingReadingNewBook() {
        // given
        LocalDate startDate = LocalDate.of(2018, 2, 5);

        // when
        readingProgressService.startReadingBook(book, reader, startDate);

        // then
        ReadingProgress readingProgress = readingProgressRepository.
                findById(new ReadingProgressKey(reader, book)).
                orElse(new ReadingProgress());

        Assertions.assertThat(readingProgress.getStart())
                .isNotNull()
                .hasYear(2018)
                .hasMonth(2)
                .hasDayOfMonth(5);

        Assertions.assertThat(readingProgress.getEnd()).isNull();
    }

    @Test
    public void shouldNotAllowStartABookThatIsAlreadyStarted() {
        // given
        LocalDate startDate = LocalDate.of(2018, 2, 9);
        readingProgressService.startReadingBook(book, reader, startDate);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Can't start already started book");

        // when
        readingProgressService.startReadingBook(book, reader);
    }

    @Test
    public void shouldNotAllowToStopABookThatIsFinished() {
        // given
        LocalDate startDate = LocalDate.of(2018, 2, 9);
        readingProgressService.startReadingBook(book, reader, startDate);

        LocalDate endDate = LocalDate.of(2018, 2, 10);
        readingProgressService.stopReadingBook(book, reader, endDate);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Can't stop already stopped book");

        // when
        readingProgressService.stopReadingBook(book, reader);
    }

    @Test
    public void stopReadingABook() {
        // given
        LocalDate startDate = LocalDate.of(2018, 2, 5);
        LocalDate stopDate = LocalDate.of(2018, 3, 5);
        readingProgressService.startReadingBook(book, reader, startDate);

        // when
        readingProgressService.stopReadingBook(book, reader, stopDate);

        ReadingProgress readingProgress = readingProgressRepository.
                findById(new ReadingProgressKey(reader, book)).
                orElse(new ReadingProgress());

        Assertions.assertThat(readingProgress.getStart()).isNotNull();
        Assertions.assertThat(readingProgress.getEnd())
                .isNotNull()
                .hasYear(2018)
                .hasMonth(3)
                .hasDayOfMonth(5);
    }

}