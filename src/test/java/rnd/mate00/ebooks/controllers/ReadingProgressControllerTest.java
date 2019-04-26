package rnd.mate00.ebooks.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(ReadingProgressController.class)
public class ReadingProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReadingProgressService readingProgressService;

    @MockBean
    private ReaderRepository readerRepository;

    @Test
    public void t() throws Exception {
        mockMvc.perform(get("/readings"))
                .andExpect(view().name("readings/readingprogress"));
    }

}