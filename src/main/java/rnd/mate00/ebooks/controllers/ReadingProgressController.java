package rnd.mate00.ebooks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rnd.mate00.ebooks.repository.ReadingProgressRepository;
import rnd.mate00.ebooks.service.ReadingProgressService;

/**
 * Created by mate00 on 10.11.18.
 */
@Controller
public class ReadingProgressController {

    @Autowired
    private ReadingProgressRepository readingProgressRepository;

    @Autowired
    private ReadingProgressService readingProgressService;

    @RequestMapping("/readings")
    public String startReadingBook() {
        System.out.println("will start reading book ");

        return "readings/readingprogress";
    }
}
