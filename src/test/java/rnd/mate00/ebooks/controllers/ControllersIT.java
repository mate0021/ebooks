package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.config.SecurityTestDatabaseConfig;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.sec.BasicSecurityConfiguration;
import rnd.mate00.ebooks.service.ReadingProgressService;
import rnd.mate00.ebooks.service.ShoppingService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@Import({BasicSecurityConfiguration.class, SecurityTestDatabaseConfig.class})
@ActiveProfiles("test")
@Sql(scripts = "classpath:fake_users.sql")
public class ControllersIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean private BookRepository bookRepository;
    @MockBean private ThemeRepository themeRepository;
    @MockBean private ReadingProgressService readingProgressService;
    @MockBean private ShoppingService shoppingService;
    @MockBean private ReaderRepository readerRepository;
    @MockBean private ShopRepository shopRepository;

    @Before
    public void setUp() {
        when(readerRepository.findByName(eq("test_reader"))).thenReturn(Optional.of(new Reader("test_reader")));
    }

    @WithMockUser(username = "test_reader")
    @Test
    public void authorizedUserCanBuyABook() throws Exception {
        mockMvc.perform(get("/books/1/buy")).andExpect(status().isOk());
    }

    @WithMockUser(username = "fake")
    @Test
    public void unauthorizedCantBuyBook() throws Exception {
        mockMvc.perform(get("/books/1/buy")).andExpect(status().isUnauthorized());
    }
}
