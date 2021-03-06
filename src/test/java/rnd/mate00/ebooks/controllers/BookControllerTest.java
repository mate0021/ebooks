package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
import rnd.mate00.ebooks.service.ShoppingService;

import java.security.Principal;
import java.time.LocalDate;
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
@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class, secure = false)
public class BookControllerTest {

    @MockBean private ReadingProgressService readingProgressService;
    @MockBean private BookRepository bookRepository;
    @MockBean private ReaderRepository readerRepository;
    @MockBean private ShopRepository shopRepository;
    @MockBean private ThemeRepository themeRepository;
    @MockBean private ThemeToThemeCommand themeToThemeCommand;
    @MockBean private BookToBookCommand bookToBookCommand;
    @MockBean private BookCommandToBook bookCommandToBook;
    @MockBean private ShoppingService shoppingService;
    @MockBean private Principal principal;

    @Autowired
    private MockMvc mockMvc;

    private Book testBook = new Book(1,"title", "author", 5000, new Theme());

    @Before
    public void setUp() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(testBook));
        when(readerRepository.findByName("mate00")).thenReturn(Optional.of(new Reader()));
        when(shopRepository.findAll()).thenReturn(asList(new Shop("shoporama", "www.shoporama.com")));
        when(principal.getName()).thenReturn("mate00");
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
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "0");
        params.add("started", "2019/10/06");

        // when
        mockMvc.perform(post("/startReading")
                .params(params)
                .principal(principal))
                .andExpect(view().name("redirect:/books/0/details"));

        // then
        verify(readingProgressService).startReadingBook(any(Book.class), any(Reader.class), any(LocalDate.class));
    }

    @Test
    public void shouldShowDetailsOfTheBook_WhenFinishReading() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "0");
        params.add("finished", "2019/10/06");

        // when
        mockMvc.perform(post("/finishReading")
                .params(params)
                .principal(principal))
                .andExpect(view().name("redirect:/books/0/details"));

        // then
        verify(readingProgressService).stopReadingBook(any(Book.class), any(Reader.class), any(LocalDate.class));
    }

    @Test
    public void shouldDisplayPurchaseForm() throws Exception {
        // given
        PurchaseCommand expectedCommand = new PurchaseCommand();
        expectedCommand.setBook(testBook);
        expectedCommand.setReader(new Reader());

        // when
        mockMvc.perform(get("/books/1/buy").principal(principal))
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
        mockMvc.perform(get("/books/1/details").principal(principal))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/bookdetails"));
    }
}