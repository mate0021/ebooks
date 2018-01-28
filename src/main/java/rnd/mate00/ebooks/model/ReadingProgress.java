package rnd.mate00.ebooks.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
@Table
public class ReadingProgress {

    // maybe compound key?
//    private Reader reader;

//    private Book book;

    @EmbeddedId
    private ReadingProgressKey key;

    private Date start;

    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

