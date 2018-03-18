package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.BookCommand;
import rnd.mate00.ebooks.converters.BookCommandToBook;
import rnd.mate00.ebooks.converters.BookToBookCommand;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.repository.BookRepository;

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

    @RequestMapping("/books")
    public String listBooks(Model model) {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        model.addAttribute("books", bookList);

        return "book/booklist";
    }

    @RequestMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookCommand());

        return "book/bookform";
    }

    @RequestMapping("/books/{id}/update")
    public String updateBook(@PathVariable String id, Model model) {
        int bookId = Integer.parseInt(id);
        Book book = bookRepository.findById(bookId).orElse(new Book());
        model.addAttribute("book", bookToBookCommand.convert(book));

        return "book/bookform";
    }

    @PostMapping
    @RequestMapping("/bookForm")
    public String saveBook(@ModelAttribute BookCommand bookCommand) {
        Book book = bookCommandToBook.convert(bookCommand);
        bookRepository.save(book);

        return "redirect:/books";
    }

    @RequestMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable String id) {
        int bookId = Integer.parseInt(id);
        bookRepository.deleteById(bookId);

        return "redirect:/books";
    }
}
