package rnd.mate00.ebooks.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.sec.BasicSecurityConfiguration;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {ThemeController.class, ShopController.class})
@Import(BasicSecurityConfiguration.class)
public class SecuredControllersIT {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ThemeRepository themeRepository;

    @MockBean
    ShopRepository shopRepository;

    @MockBean(name = "security.datasource")
    DataSource dataSource;

    @MockBean
    BCryptPasswordEncoder encoder;

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
}
