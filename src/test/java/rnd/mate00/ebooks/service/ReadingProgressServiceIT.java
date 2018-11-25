package rnd.mate00.ebooks.service;

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
import rnd.mate00.ebooks.repository.ThemeRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    private ThemeRepository themeRepository;

    private Reader reader;

    private Book book;

    private Theme theme;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        theme = themeRepository.save(new Theme("criminal"));
        reader = readerRepository.save(new Reader(1, "reader1"));
        book = bookRepository.save(new Book(1, "title", "author", 3456, theme));
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

        assertThat(readingProgress.getStart())
                .isNotNull()
                .hasYear(2018)
                .hasMonth(2)
                .hasDayOfMonth(5);

        assertThat(readingProgress.getEnd()).isNull();
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

        assertThat(readingProgress.getStart()).isNotNull();
        assertThat(readingProgress.getEnd())
                .isNotNull()
                .hasYear(2018)
                .hasMonth(3)
                .hasDayOfMonth(5);
    }

    @Test
    public void shouldGetBooksInProgress() {
        // given
        Book finishedBook = bookRepository.save(new Book("finished", "Alex Finish", 100, theme));
        Book startedBook = bookRepository.save(new Book("started", "Bob Starter", 134, theme));
        Book newBook = bookRepository.save(new Book("new", "Brand New", 123, theme));
        LocalDate startDate = LocalDate.of(2015, 11, 21);
        LocalDate endDate = LocalDate.of(2016, 1, 5);

        readingProgressService.startReadingBook(finishedBook, reader, startDate);
        readingProgressService.stopReadingBook(finishedBook, reader, endDate);

        readingProgressService.startReadingBook(startedBook, reader, startDate);

        // when
        List<ReadingProgress> booksInProgress = readingProgressService.getBooksInProgress(reader);

        // then
        assertThat(booksInProgress)
                .isNotNull()
                .isNotEmpty()
                .contains(
                        new ReadingProgress(
                                new ReadingProgressKey(reader, startedBook),
                                java.sql.Date.valueOf(startDate),
                                null)
                );
    }

    @Test
    public void shouldReturnEmptyList_WhenAllBooksFinished() {
        // given
        Book finishedBook = bookRepository.save(new Book("finished", "AlexFinish", 100, theme));

        readingProgressService.startReadingBook(finishedBook, reader);
        readingProgressService.stopReadingBook(finishedBook, reader);

        // when
        List<Book> inProgress = null;//readingProgressService.getBooksInProgress(reader);
        List<ReadingProgress> booksInProgress = readingProgressService.getBooksInProgress(reader);

        // then
        assertThat(booksInProgress)
                .isNotNull()
                .isEmpty();
    }
}