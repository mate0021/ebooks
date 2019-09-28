package rnd.mate00.ebooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rnd.mate00.ebooks.model.*;
import rnd.mate00.ebooks.repository.ShoppingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by mate00 on 12.02.18.
 */
@Service
public class ShoppingService {

    private ShoppingRepository shoppingRepository;

    @Autowired
    public ShoppingService(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }

    public void buyABook(Reader reader, Book book, Shop shop, BigDecimal price) {
        buyABook(reader, book, shop, price, LocalDate.now());
    }

    public void buyABook(Reader reader, Book book, Shop shop, BigDecimal price, LocalDate date) {
        ShoppingKey key = new ShoppingKey(book, reader, shop);
        Shopping shopping = new Shopping();
        shopping.setShoppingKey(key);
        shopping.setPrice(price);
        Date buyDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        shopping.setBuyDate(buyDate);

        if (alreadyBought(key)) {
            throw new IllegalStateException(String.format("Book was already bought %s", key));
        } else {
            shoppingRepository.save(shopping);
        }
    }

    private boolean alreadyBought(ShoppingKey key) {
        return shoppingRepository.findById(key).isPresent();
    }

    public Date getPurchaseDateFor(Book book, Reader reader) {
        List<Shopping> boughtBooks = shoppingRepository.findLatestShoppingBookByReader(book, reader);

        return boughtBooks
                .stream()
                .findFirst()
                .map(Shopping::getBuyDate)
                .orElse(null);
    }
}
