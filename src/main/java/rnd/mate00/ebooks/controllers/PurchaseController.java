package rnd.mate00.ebooks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.PurchaseCommand;

import javax.validation.Valid;

/**
 * Created by mate00 on 09.12.18.
 */
@Controller
public class PurchaseController {

    @PostMapping
    @RequestMapping("/buyBook")
    public String onBuyBook(@Valid @ModelAttribute(name = "purchase") PurchaseCommand purchaseCommand,
                          BindingResult bindingResult) {
        System.out.println(purchaseCommand);

        return "redirect:/books";
    }
}
