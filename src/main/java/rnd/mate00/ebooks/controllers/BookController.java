package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.BookCommand;
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.converters.BookCommandToBook;
import rnd.mate00.ebooks.converters.BookToBookCommand;
import rnd.mate00.ebooks.converters.ThemeToThemeCommand;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.BookRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/books")
    public String listBooks(Model model) {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        model.addAttribute("books", bookList);

        return "book/booklist";
    }

    @RequestMapping("/books/add")
    public String addBook(Model model) {
        Iterable<Theme> themeIterable = themeRepository.findAll();
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
        int bookId = Integer.parseInt(id);
        Book book = bookRepository.findById(bookId).orElse(new Book());
        BookCommand bookBackingBean = bookToBookCommand.convert(book);

        List<Theme> themes = new ArrayList<>();
        themeRepository.findAll().forEach(t -> themes.add(t));
        model.addAttribute("book", bookBackingBean);
        model.addAttribute("themeList", themes);

        return "book/bookform";
    }

    @PostMapping
    @RequestMapping("/bookForm")
    public String saveBook(@ModelAttribute BookCommand bookCommand) {
        Book book = bookCommandToBook.convert(bookCommand);
        Book savedBook = bookRepository.save(book);

        return "redirect:/books";
    }

    @RequestMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable String id) {
        int bookId = Integer.parseInt(id);
        bookRepository.deleteById(bookId);

        return "redirect:/books";
    }

    @RequestMapping("/books/{id}/details")
    public String bookDetails(Model model) {
        List<BookCommand> bookBeans = new ArrayList<>();
        bookRepository.findAll().forEach(b -> bookBeans.add(bookToBookCommand.convert(b)));
        model.addAttribute("allBooks", bookBeans);

        return "book/bookdetails";
    }
}
