package rnd.mate00.ebooks.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.config.SecurityTestConfig;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
@Import(SecurityTestConfig.class)
@ActiveProfiles("test")
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private ReaderRepository readerRepository;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private ReadingProgressService readingProgressService;


    @Test
    public void shouldReturnUnauthorizedStatus() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldNotAllowBookUpdate() throws Exception {
        mockMvc.perform(get("/books/1/update")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldAllowFormHandling() throws Exception {
        mockMvc.perform(post("/bookForm")).andExpect(status().isOk());
    }
}
