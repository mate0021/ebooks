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
                .andExpect(model().attributeHasErrors("reader"))
                .andExpect(model().attributeHasFieldErrors("reader", "password", "repeatedPassword"))
                .andExpect(view().name("reader/readerform"));
    }

}