package rnd.mate00.ebooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.sec.Role;
import rnd.mate00.ebooks.sec.RoleRepository;
import rnd.mate00.ebooks.sec.SecureReader;
import rnd.mate00.ebooks.sec.SecureReaderRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mate00 on 05.03.18.
 */
@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private SecureReaderRepository secureReaderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addShops();
        addThemes();
        addBooks();
        addReaders();
        addRoles();
        addSecurityUsers();
    }

    private void addShops() {
        Shop ebookPoint = new Shop("Ebookpoint", "www.ebookpoint.pl");
        Shop virtualo = new Shop("Virtualo", "www.virtualo.pl");
        Shop publio = new Shop("Publio", "www.publio.pl");

        shopRepository.save(ebookPoint);
        shopRepository.save(virtualo);
        shopRepository.save(publio);

        System.out.println(String.format("Added %d shops.", shopRepository.count()));
    }

    private void addThemes() {
        Theme fact = new Theme("Reportaż");
        Theme historical = new Theme("Historyczna");
        Theme criminal = new Theme("Kryminał");
        Theme sensation = new Theme("Sensacja");

        themeRepository.save(fact);
        themeRepository.save(historical);
        themeRepository.save(criminal);
        themeRepository.save(sensation);

        System.out.println(String.format("Added %s themes.", themeRepository.count()));
    }

    private void addBooks() {
        Book b1 = new Book("Akwarium", "W. Suworow", 4567, themeRepository.findByTheme("Historyczna").orElse(new Theme()));
        Book b2 = new Book("Łatwo być bogiem", "R. Szmidt", 6789, themeRepository.findByTheme("Sensacja").orElse(new Theme()));
        Book b3 = new Book("Wektor zagrożenia", "T. Clancy", 8909, themeRepository.findByTheme("Sensacja").orElse(new Theme()));

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);

        System.out.println(String.format("Added %s books.", bookRepository.count()));
    }

    private void addReaders() {
        Reader reader = new Reader("mate00");

        readerRepository.save(reader);

        System.out.println(String.format("Added %s reader(s).", readerRepository.count()));
    }

    private void addRoles() {
        Role roleAdmin = new Role(1, "admin");
        Role roleReader = new Role(2, "reader");

        roleRepository.save(roleAdmin);
        roleRepository.save(roleReader);

        System.out.println(String.format("Added %s system role(s)", roleRepository.count()));
    }

    private void addSecurityUsers() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(1).get());
        roles.add(roleRepository.findById(2).get());

        SecureReader reader =
                new SecureReader(
                        1,
                        "mate00",
                        "$2a$10$sGx1EawJhzFIaYJHLDSs5ewQqzCO6M6jGAZnMcw6RUAsuAwe2CUMK",
                        true,
                        roles);

        secureReaderRepository.save(reader);

        System.out.println(String.format("Added %s user(s).", secureReaderRepository.count()));
    }
}
