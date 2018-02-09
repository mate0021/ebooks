package rnd.mate00.ebooks.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Theme;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mate00 on 23.01.18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ThemeRepository themeRepository;

//    @Test
    public void thereIsABookInRepo() {
        assertThat(bookRepository.findAll()).isNotEmpty();
    }

    @Test
    public void shouldAddBookWithTheme() {
        Theme theme = new Theme("Kryminał");
        themeRepository.save(theme);

        Book book = new Book("Czerwone gardło", "Jo Nesbo", 6000, theme);
        bookRepository.save(book);

        Book bookByTitle = bookRepository.findByTitle("Czerwone gardło").orElseThrow(RuntimeException::new);
        assertThat(bookByTitle.getTheme()).isNotNull();
    }

}