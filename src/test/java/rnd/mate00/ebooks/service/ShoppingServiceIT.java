package rnd.mate00.ebooks.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rnd.mate00.ebooks.model.*;
import rnd.mate00.ebooks.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mate00 on 12.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase
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

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        assertThat(shoppingRepository.findById(new ShoppingKey(book, reader, shop)).isPresent()).isTrue();
    }

    @Test
    public void readerBuysTheSameBookInDifferentShop() {
        // given
        Shop otherShop = shopRepository.save(new Shop("Ebookpoint", ""));

        // when
        subject.buyABook(reader, book, shop, new BigDecimal(21));
        subject.buyABook(reader, book, otherShop, new BigDecimal(21));

        // then
        assertThat(shoppingRepository.findById(new ShoppingKey(book, reader, shop)).isPresent()).isTrue();
        assertThat(shoppingRepository.findById(new ShoppingKey(book, reader, otherShop)).isPresent()).isTrue();
    }

    @Test(expected = IllegalStateException.class)
    public void readerCantBuyTheSameBookInTheSameShop() {
        // when
        subject.buyABook(reader, book, shop, new BigDecimal(21));
        subject.buyABook(reader, book, shop, new BigDecimal(21));

        // then
        Iterable<Shopping> allById = shoppingRepository.findAllById(Arrays.asList(new ShoppingKey(book, reader, shop)));
        assertThat(allById.spliterator().getExactSizeIfKnown()).isEqualTo(1);
    }

    @Test
    public void readerCantBuyTheSameBookTwice_ExpectError() {
        expectedException.expect(IllegalStateException.class);

        // when
        subject.buyABook(reader, book, shop, new BigDecimal(10.50));
        subject.buyABook(reader, book, shop, new BigDecimal(10.50));
    }

    @Test
    public void otherReaderBuysTheSameBook() {
        // given
        Reader otherReader = readerRepository.save(new Reader("other"));

        // when
        subject.buyABook(reader, book, shop, new BigDecimal(21));
        subject.buyABook(otherReader, book, shop, new BigDecimal(14));

        // then
        assertThat(shoppingRepository.findById(new ShoppingKey(book, reader, shop)).isPresent()).isTrue();
        assertThat(shoppingRepository.findById(new ShoppingKey(book, otherReader, shop)).isPresent()).isTrue();
    }

    @Test
    public void shouldReturnLatestPurchaseOfBookForReader() {
        // given
        subject.buyABook(reader, book, shop, new BigDecimal(10.99), LocalDate.of(2019, 9, 28));

        // when
        Date result = subject.getPurchaseDateFor(book, reader);

        // then
        assertThat(result).isEqualTo("2019-09-28");
    }

    @Test
    public void shouldReturnLatestPurchaseDate_WhenBoughtInDifferentShops() {
        // given
        Shop otherShop = shopRepository.save(new Shop("Ebookpoint", ""));
        subject.buyABook(reader, book, shop, new BigDecimal(10.99), LocalDate.of(2018, 8, 28));
        subject.buyABook(reader, book, otherShop, new BigDecimal(9.99), LocalDate.of(2019, 9, 28));

        // when
        Date result = subject.getPurchaseDateFor(book, reader);

        // then
        assertThat(result).isEqualTo("2019-09-28");
    }
}
