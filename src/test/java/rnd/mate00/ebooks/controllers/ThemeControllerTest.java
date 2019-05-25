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
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.converters.ThemeCommandToTheme;
import rnd.mate00.ebooks.converters.ThemeToThemeCommand;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.ThemeRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ThemeController.class, secure = false)
public class ThemeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private ThemeCommandToTheme themeCommandToTheme;

    @MockBean
    private ThemeToThemeCommand themeToThemeCommand;


    @Test
    public void shouldDisplayThemes() throws Exception {
        mockMvc.perform(get("/themes"))
                .andExpect(model().attributeExists("themes"))
                .andExpect(status().isOk())
                .andExpect(view().name("theme/themelist"));
    }

    @Test
    public void shouldDisplayAddThemeView() throws Exception {
        mockMvc.perform(get("/themes/add"))
                .andExpect(model().attributeExists("theme"))
                .andExpect(status().isOk())
                .andExpect(view().name("theme/themeform"));
    }

    @Test
    public void shouldRedirectToThemeViewAfterSaveNewTheme() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("theme", "theme name");

        mockMvc.perform(post("/themeForm").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/themes"));
    }

    @Test
    public void shouldSaveNewTheme() throws Exception {
        when(themeCommandToTheme.convert(any(ThemeCommand.class))).thenReturn(new Theme());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("theme", "theme name");

        mockMvc.perform(post("/themeForm").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/themes"));

        verify(themeRepository).save(any(Theme.class));

    }

    @Test
    public void shouldRedirectToThemeViewAfterDeleteTheme() throws Exception {
        mockMvc.perform(get("/theme/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/themes"));
    }

    @Test
    public void shouldDeleteTheme() throws Exception {
        mockMvc.perform(get("/theme/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/themes"));

        verify(themeRepository).deleteById(1);
    }

    @Test
    public void shouldDisplayAddThemeView_WithValues() throws Exception {
        when(themeToThemeCommand.convert(any(Theme.class))).thenReturn(new ThemeCommand(1, "theme name"));

        mockMvc.perform(get("/theme/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("theme"))
                .andExpect(view().name("theme/themeform"));
    }
}