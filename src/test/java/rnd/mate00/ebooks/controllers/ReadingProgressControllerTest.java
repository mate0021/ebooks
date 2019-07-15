package rnd.mate00.ebooks.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReadingProgressController.class, secure = false)
public class ReadingProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReadingProgressService readingProgressService;

    @MockBean
    private ReaderRepository readerRepository;

    @Mock
    private Principal principal;

    @Before
    public void setUp() {
        when(principal.getName()).thenReturn("mate00");
        when(readerRepository.findByName("mate00")).thenReturn(Optional.of(new Reader("mate00")));
    }

    @Test
    public void shouldDisplayBooksInProgress() throws Exception {
        mockMvc.perform(get("/readings").principal(principal))
                .andExpect(view().name("readings/readingprogress"))
                .andExpect(model().attributeExists("booksInProgress"));
    }

}