package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.commands.BookInProgressCommand;
import rnd.mate00.ebooks.converters.ReadingProgressToBookInProgressCommand;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.ReadingProgress;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by mate00 on 10.11.18.
 */
@Controller
public class ReadingProgressController {

    @Autowired
    private ReadingProgressService readingProgressService;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReadingProgressToBookInProgressCommand readingProgressToBookInProgressCommand;


    @RequestMapping("/readings")
    public String showBooksInProgress(Model model) {
        Reader reader = readerRepository.findById(1).get();
        List<ReadingProgress> booksInProgress = readingProgressService.getBooksInProgress(reader);

        List<BookInProgressCommand> booksInProgressBackingBean = booksInProgress.
                stream().
                map(bip -> readingProgressToBookInProgressCommand.convert(bip)).
                collect(toList());

        model.addAttribute("booksInProgress", booksInProgressBackingBean);

        return "readings/readingprogress";
    }

}
