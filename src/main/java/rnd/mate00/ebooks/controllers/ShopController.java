package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.repository.ShopRepository;

/**
 * Created by mate00 on 03.03.18.
 */
@Controller
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @RequestMapping("/")
    public String mainShopController(Model model) {
        System.out.println("we're in shop controller");

        model.addAttribute(new Shop("virt", "www"));
        
        return "shop/shopform";
    }
}
