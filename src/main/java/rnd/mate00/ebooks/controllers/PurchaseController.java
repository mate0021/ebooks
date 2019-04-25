package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.PurchaseCommand;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.service.ShoppingService;

import javax.validation.Valid;
import java.time.LocalDate;

import static java.time.ZoneId.systemDefault;

/**
 * Created by mate00 on 09.12.18.
 */
@Controller
public class PurchaseController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ShopRepository shopRepository;


    @PostMapping
    @RequestMapping("/buyBook")
    public String onBuyBook(@Valid @ModelAttribute(name = "purchase") PurchaseCommand purchaseBean,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shopList", shopRepository.findAll());
            return "shopping/purchasedetails";
        }

        LocalDate buyDate = purchaseBean.getBuyDate().toInstant().atZone(systemDefault()).toLocalDate();
        shoppingService.buyABook(purchaseBean.getReader(), purchaseBean.getBook(), purchaseBean.getShop(), purchaseBean.getPrice(), buyDate);

        return "redirect:/books";
    }
}
