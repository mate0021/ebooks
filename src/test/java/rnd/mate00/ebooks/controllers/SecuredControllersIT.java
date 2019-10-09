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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rnd.mate00.ebooks.config.SecurityTestDatabaseConfig;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.repository.*;
import rnd.mate00.ebooks.sec.BasicSecurityConfiguration;
import rnd.mate00.ebooks.service.ReadingProgressService;
import rnd.mate00.ebooks.service.ShoppingService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ThemeController.class, ShopController.class, BookController.class, ReadingProgressController.class, PurchaseController.class})
@Import({BasicSecurityConfiguration.class, SecurityTestDatabaseConfig.class})
@ActiveProfiles("test")
@Sql(scripts = "classpath:fake_users.sql")
public class SecuredControllersIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ThemeRepository themeRepository;

    @MockBean
    ShopRepository shopRepository;

    @MockBean
    BookRepository bookRepository;

    @MockBean
    ReadingProgressRepository readingProgressRepository;

    @MockBean
    ReadingProgressService readingProgressService;

    @MockBean
    ReaderRepository readerRepository;

    @MockBean
    ShoppingService shoppingService;


    @MockBean
    BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        when(readerRepository.findByName(eq("test_reader"))).thenReturn(Optional.of(new Reader("test_reader")));
    }

    @Test
    public void unauthUserCanSeeThemes() throws Exception {
        mockMvc.perform(get("/themes")).andExpect(status().isOk());
    }

    @Test
    public void unauthUserCannotModifyThemes() throws Exception {
        mockMvc.perform(post("/themeForm")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotAddTheme() throws Exception {
        mockMvc.perform(get("/themes/add")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCanSeeShops() throws Exception {
        mockMvc.perform(get("/shops")).andExpect(status().isOk());
    }

    @Test
    public void unauthUserCannotModifyShop() throws Exception {
        mockMvc.perform(post("/shopForm")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotAddShop() throws Exception {
        mockMvc.perform(get("/shops/add")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotSeeBooksInProgress() throws Exception {
        mockMvc.perform(get("/readings")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotBuyABook() throws Exception {
        mockMvc.perform(get("/books/1/buy")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotStartReadingABook() throws Exception {
        mockMvc.perform(get("/books/1/start")).andExpect(status().is4xxClientError());
    }

    @Test
    public void unauthUserCannotFinishReadingABook() throws Exception {
        mockMvc.perform(get("/books/1/finish")).andExpect(status().is4xxClientError());
    }

    @WithMockUser
    @Test
    public void authUserCanViewBooks() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void authUserCanAddShop() throws Exception {
        mockMvc.perform(get("/shops/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void authUserCanUpdateShop() throws Exception {
        mockMvc.perform(post("/shopForm")).andExpect(status().is3xxRedirection());
    }

    @WithMockUser
    @Test
    public void authUserCanAddTheme() throws Exception {
        mockMvc.perform(get("/themes/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void authUserCanUpdateTheme() throws Exception {
        mockMvc.perform(post("/themeForm")).andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "test_reader")
    @Test
    public void authUserCanSeeBooksInProgress() throws Exception {
        mockMvc.perform(get("/readings")).andExpect(status().isOk());
    }

    @WithMockUser(username = "test_reader")
    @Test
    public void authUserCanBuyABook() throws Exception {
        mockMvc.perform(get("/books/1/buy")).andExpect(status().isOk());
    }

    @WithMockUser(username = "test_reader")
    @Test
    public void authUserCanStartReadingABook() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "0");
        params.add("started", "2019/10/06");

        mockMvc.perform(post("/startReading").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/0/details"));
    }

    @WithMockUser(username = "test_reader")
    @Test
    public void authUserCanFinishReadingABook() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "0");
        params.add("finished", "2019/10/06");

        mockMvc.perform(post("/finishReading").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/0/details"));
    }

}
