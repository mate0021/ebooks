package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.converters.ThemeCommandToTheme;
import rnd.mate00.ebooks.converters.ThemeToThemeCommand;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.ThemeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mate00 on 12.03.18.
 */
@Controller
public class ThemeController {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeCommandToTheme themeCommandToTheme;

    @Autowired
    private ThemeToThemeCommand themeToThemeCommand;

    @RequestMapping("/themes")
    public String themeList(Model model){
        List<Theme> themeList = new ArrayList<>();
        themeRepository.findAll().iterator().forEachRemaining(themeList::add);
        model.addAttribute("themes", themeList);

        return "theme/themelist";
    }

    @RequestMapping("/themes/add")
    public String addTheme(Model model) {
        model.addAttribute("theme", new ThemeCommand());

        return "theme/themeform";
    }

    @PostMapping
    @RequestMapping("/themeForm")
    public String onThemeUpdate(@ModelAttribute ThemeCommand themeCommand) {
        themeRepository.save(themeCommandToTheme.convert(themeCommand));

        return "redirect:/themes";
    }

    @RequestMapping("/theme/{id}/delete")
    public String deleteTheme(@PathVariable String id) {
        int themeId = Integer.parseInt(id);
        themeRepository.deleteById(themeId);

        return "redirect:/themes";
    }

    @RequestMapping("/theme/{id}/update")
    public String updateTheme(@PathVariable String id, Model model) {
        int themeId = Integer.parseInt(id);
        Theme theme = themeRepository.findById(themeId).orElse(new Theme());
        model.addAttribute("theme", themeToThemeCommand.convert(theme));

        return "theme/themeform";
    }
}
