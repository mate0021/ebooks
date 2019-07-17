package rnd.mate00.ebooks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.ReaderCommand;

import javax.validation.Valid;

@Controller
public class ReaderController {

    @GetMapping("/reader/add")
    public String addNewReader(Model model) {
        ReaderCommand readerCommand = new ReaderCommand();

        model.addAttribute("reader", readerCommand);

        return "reader/readerform";
    }

    @PostMapping
    @RequestMapping("/readerForm")
    public String onUpdateReader(@Valid @ModelAttribute(name = "reader") ReaderCommand readerBean,
                                 BindingResult bindingResult,
                                 Model model) {
        System.out.println(readerBean);
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            model.addAttribute("reader", readerBean);
            return "reader/readerform";
        }

        return "redirect:/books";
    }
}