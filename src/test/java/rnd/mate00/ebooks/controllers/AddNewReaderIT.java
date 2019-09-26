package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rnd.mate00.ebooks.service.ReaderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ReaderController.class)
public class AddNewReaderIT {

    @InjectMocks
    private ReaderController subject;

    private MockMvc mockMvc;

    @Mock
    private ReaderService readerService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void addNewReader_DemandsTwoPasswords() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "somename");
        params.add("email", "some@email");

        mockMvc.perform(post("/readerForm").params(params))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("reader", "password", "repeatedPassword"))
                .andExpect(view().name("reader/readerform"));
    }

    @Test
    public void addNewReader_PasswordsShouldMatch() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "somename");
        params.add("email", "some@email");
        params.add("password", "adminadmin");
        params.add("repeatedPassword", "admin1");

        mockMvc.perform(post("/readerForm").params(params))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1)) // hasFieldErrors doesn't work in this case
//                .andExpect(model().attributeHasFieldErrors("reader", "password", "repeatedPassword"))
                .andExpect(view().name("reader/readerform"));
    }

    @Test
    public void addNewReader_RedirectToBookList() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "somename");
        params.add("email", "some@email");
        params.add("password", "admin");
        params.add("repeatedPassword", "admin");

        mockMvc.perform(post("/readerForm").params(params))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));
    }

}