package rnd.mate00.ebooks.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.service.ShoppingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

    @MockBean
    private ShoppingService shoppingService;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSameViewWithError_WhenEmptyDate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "1");
        params.add("reader.id", "1");
        params.add("buyDate", null);

        mockMvc.perform(post("/buyBook").params(params)).andExpect(view().name("shopping/purchasedetails"));
    }

    @Test
    public void shouldReturnSameViewWithError_WhenEmptyPrice() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "1");
        params.add("reader.id", "1");
        params.add("buyDate", "2018/09/09");
        params.add("price", null);

        mockMvc.perform(post("/buyBook").params(params)).andExpect(view().name("shopping/purchasedetails"));
    }

    @Test
    public void shouldReturnSameViewWithError_WhenPriceLessThanZero() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "1");
        params.add("reader.id", "1");
        params.add("buyDate", "2018/09/09");
        params.add("price", "-10.99");

        mockMvc.perform(post("/buyBook").params(params)).andExpect(view().name("shopping/purchasedetails"));
    }

    @Test
    public void shouldAcceptPurchase() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("book.id", "1");
        params.add("reader.id", "1");
        params.add("buyDate", "2018/09/09");
        params.add("price", "8.99");
        params.add("shop.id", null);

        mockMvc.perform(post("/buyBook").params(params)).andExpect(status().is3xxRedirection());
    }
}
