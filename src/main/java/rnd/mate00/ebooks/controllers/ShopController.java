package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.ShopCommand;
import rnd.mate00.ebooks.converters.ShopCommandToShop;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mate00 on 03.03.18.
 */
@Controller
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopCommandToShop shopCommandToShop;


    @PostMapping
    @RequestMapping("shopForm")
    public String savingShop(@ModelAttribute ShopCommand shopCommand) {
        Shop savedShop = shopRepository.save(shopCommandToShop.convert(shopCommand));

        return "redirect:/shops";
    }

    @RequestMapping("/shops")
    public String shopList(Model model) {
        List<Shop> shops = new ArrayList<>();
        shopRepository.findAll().iterator().forEachRemaining(shops::add);
        model.addAttribute("bookstores", shops);

        return "shop/shoplist";
    }

    @RequestMapping("/shops/add")
    public String addShop(Model model) {
        model.addAttribute("shop", new ShopCommand());

        return "shop/shopform";
    }
}
