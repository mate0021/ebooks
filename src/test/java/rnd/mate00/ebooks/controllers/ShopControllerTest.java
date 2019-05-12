package rnd.mate00.ebooks.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.converters.ShopCommandToShop;
import rnd.mate00.ebooks.repository.ShopRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ShopController.class, secure = false)
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private ShopCommandToShop shopCommandToShop;

    @Test
    public void shouldDisplayListOfShops() throws Exception {
        mockMvc.perform(get("/shops"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/shoplist"))
                .andExpect(model().attributeExists("bookstores"));
    }

    @Test
    public void shouldDisplayAddigShopForm() throws Exception {
        mockMvc.perform(get("/shops/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop/shopform"))
                .andExpect(model().attributeExists("shop"));
    }

    @Test
    public void shouldAddNewShop() throws Exception {
        mockMvc.perform(post("/shopForm"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/shops"));
    }

}