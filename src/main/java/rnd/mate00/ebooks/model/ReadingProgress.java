package rnd.mate00.ebooks.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;

/**
 * Created by mate00 on 21.01.18.
 */
public class ReadingProgress {

    // maybe compound key?
    @ManyToMany
    private Reader reader;

    @ManyToMany
    private Book book;

    private Date start;

    private Date end;
}
