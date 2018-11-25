package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.ReadingProgress;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import java.util.Date;
import java.util.List;

/**
 * Created by mate00 on 10.11.18.
 */
@Controller
public class ReadingProgressController {

    @Autowired
    private ReadingProgressService readingProgressService;

    @Autowired
    private ReaderRepository readerRepository;

    @RequestMapping("/readings")
    public String showBooksInProgress(Model model) {
        Reader reader = readerRepository.findById(1).get();
        List<ReadingProgress> booksInProgress = readingProgressService.getBooksInProgress(reader);

        List<BookInProgressCommand> booksInProgressBackingBean = null;//Arrays.asList(new BookInProgressCommand(booksInProgress.get(0), new Date()));
        model.addAttribute("booksInProgress", booksInProgressBackingBean);

        return "readings/readingprogress";
    }

    static class BookInProgressCommand {
        Book book;
        Date started;
        Date finished;

        public BookInProgressCommand(Book book, Date started, Date finished) {
            this.book = book;
            this.started = started;
            this.finished = finished;
        }

        public int getId() {
            return book.getId();
        }

        public String getTitle() {
            return book.getTitle();
        }

        public String getAuthor() {
            return book.getAuthor();
        }

        public Date getStarted() {
            return started;
        }

        public Date getFinished() {
            return finished;
        }
    }
}
