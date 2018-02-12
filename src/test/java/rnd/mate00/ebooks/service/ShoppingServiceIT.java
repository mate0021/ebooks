package rnd.mate00.ebooks.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by mate00 on 12.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingServiceIT {

    @Autowired
    private ShoppingService subject;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ThemeRepository themeRepository;

    private Book book;

    private Reader reader;

    private Shop shop;

    @Before
    public void setUp() {
        book = bookRepository.save(new Book("Italian Job", "G. Vialli", 4500, themeRepository.save(new Theme("Football"))));
        reader = readerRepository.save(new Reader("mate00"));
        shop = shopRepository.save(new Shop("Virtualo", ""));
    }

    @Test
    public void readerBuysABook() {
        // given
        BigDecimal price = new BigDecimal(21);

        // when
        subject.buyABook(reader, book, shop, price);

        // then
    }

    @Test
    public void readerBuysTheSameBookInDifferentShop() {}

    @Test
    public void readerCantBuyTheSameBookInTheSameShop() {}

    @Test
    public void otherReaderBuysTheSameBook() {}
}