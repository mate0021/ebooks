package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/books")
    public String listBooks(Model model) {
        List<Book> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        model.addAttribute("books", bookList);

        return "book/booklist";
    }
}
