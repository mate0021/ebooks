package rnd.mate00.ebooks.commands;

import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Theme;

import java.util.Date;

/**
 * Created by mate00 on 26.11.18.
 */
public class BookInProgressCommand {

    private Book book;
    private Date started;
    private Date finished;

    public BookInProgressCommand(Book book, Date started, Date finished) {
        this.book = book;
        this.started = started;
        this.finished = finished;
    }

    public int getId() {
        return book.getId();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getStarted() {
        return started;
    }

    public String getTitle() {
        return book.getTitle();
    }

    public String getAuthor() {
        return book.getAuthor();
    }

    public int getLocations() {
        return book.getLocations();
    }

    public Theme getTheme() {
        return book.getTheme();
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "BookInProgressCommand{" +
                "book=" + book +
                ", started=" + started +
                ", finished=" + finished +
                '}';
    }
}
