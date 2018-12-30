package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by mate00 on 16.09.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @InjectMocks
    private BookController subject;

    @Mock
    private ReadingProgressService readingProgressService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReaderRepository readerRepository;

    private MockMvc mockMvc;

    private Book testBook = new Book("title", "author", 5000, new Theme());

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        when(bookRepository.findById(1)).thenReturn(Optional.of(testBook));
        when(readerRepository.findById(1)).thenReturn(Optional.of(new Reader()));
    }

    @Test
    public void shouldShowAllBooks_OnRoot() throws Exception {
        // when
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/booklist"));
    }

    @Test
    public void shouldShowDetailsOfTheBook_WhenStartReading() throws Exception {
        // when
        mockMvc.perform(get("/books/1/start"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", testBook))
                .andExpect(view().name("book/bookdetails"));

        // then
        verify(readingProgressService).startReadingBook(any(Book.class), any(Reader.class));
    }

    @Test
    public void shouldShowDetailsOfTheBook_WhenFinishReading() throws Exception {
        // when
        mockMvc.perform(get("/books/1/finish"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", testBook))
                .andExpect(view().name("book/bookdetails"));

        // then
        verify(readingProgressService).stopReadingBook(any(Book.class), any(Reader.class));
    }
}