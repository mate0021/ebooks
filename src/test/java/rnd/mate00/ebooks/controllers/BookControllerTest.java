package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rnd.mate00.ebooks.commands.BookCommand;
import rnd.mate00.ebooks.commands.PurchaseCommand;
import rnd.mate00.ebooks.converters.BookCommandToBook;
import rnd.mate00.ebooks.converters.BookToBookCommand;
import rnd.mate00.ebooks.converters.ThemeToThemeCommand;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ThemeRepository themeRepository;

    @Mock
    private ThemeToThemeCommand themeToThemeCommand;

    @Mock
    private BookToBookCommand bookToBookCommand;

    @Mock
    private BookCommandToBook bookCommandToBook;

    private MockMvc mockMvc;

    private Book testBook = new Book("title", "author", 5000, new Theme());

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        when(bookRepository.findById(1)).thenReturn(Optional.of(testBook));
        when(readerRepository.findById(1)).thenReturn(Optional.of(new Reader()));
        when(shopRepository.findAll()).thenReturn(asList(new Shop("shoporama", "www.shoporama.com")));
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
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/1/details"));

        // then
        verify(readingProgressService).startReadingBook(any(Book.class), any(Reader.class));
    }

    @Test
    public void shouldShowDetailsOfTheBook_WhenFinishReading() throws Exception {
        // when
        mockMvc.perform(get("/books/1/finish"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/1/details"));

        // then
        verify(readingProgressService).stopReadingBook(any(Book.class), any(Reader.class));
    }

    @Test
    public void shouldDisplayPurchaseForm() throws Exception {
        // given
        PurchaseCommand expectedCommand = new PurchaseCommand();
        expectedCommand.setBook(testBook);
        expectedCommand.setReader(new Reader());

        // when
        mockMvc.perform(get("/books/1/buy"))
                .andExpect(status().isOk())
                .andExpect(view().name("shopping/purchasedetails"))
                .andExpect(model().attributeExists("purchase", "shopList"))
                .andExpect(model().attribute("purchase", expectedCommand))
                .andExpect(model().attribute("shopList", asList(new Shop("shoporama", "www.shoporama.com"))));
    }

    @Test
    public void shouldDisplayBookForm() throws Exception {
        mockMvc.perform(get("/books/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"))
                .andExpect(model().attributeExists("themeList", "book"));
    }

    @Test
    public void shouldDisplayUpdateBookForm() throws Exception {
        when(bookToBookCommand.convert(any(Book.class))).thenReturn(new BookCommand());

        mockMvc.perform(get("/books/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book", "themeList"));
    }

    @Test
    public void shouldDisplayBookUpdateFormWithBookValues() throws Exception {
        BookCommand bookCommand = new BookCommand(1, "title", "author", 5000, new Theme());
        when(bookToBookCommand.convert(testBook)).thenReturn(bookCommand);

        mockMvc.perform(get("/books/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", bookCommand));
    }

    @Test
    public void shouldSaveNewBook() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("author", "Author");
        params.add("locations", "1234");

        mockMvc.perform(post("/bookForm").params(params))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldReturnSameViewWithError_WhenTitleEmpty() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("author", "Author");
        params.add("locations", "1234");

        mockMvc.perform(post("/bookForm").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"));
    }

    @Test
    public void shouldReturnSameViewWithError_WhenAuthorEmpty() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("locations", "1234");

        mockMvc.perform(post("/bookForm").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"));
    }

    @Test
    public void shouldReturnSameViewWithError_WhenLocationsTooLow() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("author", "Author");
        params.add("title", "Title");
        params.add("locations", "0");

        mockMvc.perform(post("/bookForm").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("book/bookform"));
    }

    @Test
    public void shouldReturnBookDetailsViewWithReadingProgress() throws Exception {
        mockMvc.perform(get("/books/1/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/bookdetails"));
    }
}