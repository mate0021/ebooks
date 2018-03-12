package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.converters.ThemeCommandToTheme;
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
}
