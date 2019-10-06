package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.BookCommand;
import rnd.mate00.ebooks.commands.BookInProgressCommand;
import rnd.mate00.ebooks.commands.PurchaseCommand;
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.converters.BookCommandToBook;
import rnd.mate00.ebooks.converters.BookToBookCommand;
import rnd.mate00.ebooks.converters.ThemeToThemeCommand;
import rnd.mate00.ebooks.model.*;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;
import rnd.mate00.ebooks.service.ShoppingService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * Created by mate00 on 17.03.18.
 */
@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCommandToBook bookCommandToBook;

    @Autowired
    private BookToBookCommand bookToBookCommand;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeToThemeCommand themeToThemeCommand;

    @Autowired
    private ReadingProgressService readingProgressService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShoppingService shoppingService;


    @RequestMapping({"/books", "/"})
    public String listBooks(Model model) {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        model.addAttribute("books", bookList);

        return "book/booklist";
    }

    @RequestMapping("/books/add")
    public String addBook(Model model) {
        List<ThemeCommand> themes = new ArrayList<>();
        themeRepository.findAll().forEach(t -> themes.add(themeToThemeCommand.convert(t)));
        model.addAttribute("themeList", themes);
        BookCommand bookCommand = new BookCommand();
        bookCommand.setThemeCommand(new ThemeCommand());
        model.addAttribute("book", bookCommand);

        return "book/bookform";
    }

    @RequestMapping("/books/{id}/update")
    public String updateBook(@PathVariable String id, Model model) {
        Book book = findBookById(id);
        BookCommand bookBackingBean = bookToBookCommand.convert(book);

        List<Theme> themes = new ArrayList<>();
        themeRepository.findAll().forEach(t -> themes.add(t));
        model.addAttribute("book", bookBackingBean);
        model.addAttribute("themeList", themes);

        return "book/bookform";
    }

    @PostMapping
    @RequestMapping("/bookForm")
    public String saveBook(@Valid @ModelAttribute(name = "book") BookCommand bookCommand,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/bookform";
        }

        Book book = bookCommandToBook.convert(bookCommand);
        Book savedBook = bookRepository.save(book);

        return "redirect:/books";
    }

//    @RequestMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable String id) {
        int bookId = parseInt(id);
        bookRepository.deleteById(bookId);

        return "redirect:/books";
    }

    @RequestMapping("/books/{id}/details")
    public String bookDetails(@PathVariable String id, Model model, Principal principal) {
        Reader reader = readerRepository.findByName(principal.getName()).get();
        Book book = findBookById(id);
        Optional<ReadingProgress> readingProgress =
                readingProgressService.getReadingProgressFor(book, reader);

        model.addAttribute("book", getProgressBeanForBook(book, readingProgress));
        model.addAttribute("buyDate", shoppingService.getPurchaseDateFor(book, reader));
        model.addAttribute("startDate", LocalDate.now().toString());

        return "book/bookdetails";
    }

    @PostMapping
    @RequestMapping("/startReading")
    public String startReadingBook(BookInProgressCommand bookInProgress, Principal principal) {
        System.out.println(bookInProgress);
        int bookId = bookInProgress.getId();
        Book book = findBookById(String.valueOf(bookId));
        Reader loggedReader = readerRepository
                .findByName(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        LocalDate startingDate = bookInProgress.getStarted().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        readingProgressService.startReadingBook(book, loggedReader, startingDate);

        return "redirect:/books/" + bookId + "/details";
    }

    @RequestMapping("/books/{id}/start")
    public String startReading(@PathVariable String id, Model model, Principal principal) {
        Book book = findBookById(id);
        Reader loggedReader = readerRepository
                .findByName(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        readingProgressService.startReadingBook(book, loggedReader);

        return String.format("redirect:/books/%s/details", id);
    }

    @RequestMapping("/books/{id}/finish")
    public String finishReading(@PathVariable String id, Model model, Principal principal) {
        Book book = findBookById(id);
        Reader reader = readerRepository
                .findByName(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        readingProgressService.stopReadingBook(book, reader);

        return String.format("redirect:/books/%s/details", id);
    }

    private BookInProgressCommand getProgressBeanForBook(Book book, Optional<ReadingProgress> readingProgress) {
        Date started = readingProgress.map(ReadingProgress::getStart).orElse(null);
        Date finished = readingProgress.map(ReadingProgress::getEnd).orElse(null);

        return new BookInProgressCommand(book, started, finished);
    }

    @RequestMapping("/books/{id}/buy")
    public String buyBook(@PathVariable String id, Model model, Principal principal) {
        Book book = findBookById(id);

        PurchaseCommand purchaseBean = new PurchaseCommand();
        purchaseBean.setBook(book);
        purchaseBean.setReader(
                readerRepository.
                        findByName(principal.getName()).
                        orElseThrow(() -> new UsernameNotFoundException(principal.getName()))
        );
        List<Shop> shops = new ArrayList<>();
        shopRepository.findAll().forEach(s -> shops.add(s));

        model.addAttribute("purchase", purchaseBean);
        model.addAttribute("shopList", shops);

        return "shopping/purchasedetails";
    }

    private Book findBookById(String id) {
        return bookRepository.findById(parseInt(id)).orElse(new Book());
    }
}
