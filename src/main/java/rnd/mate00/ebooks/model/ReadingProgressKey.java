package rnd.mate00.ebooks.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReadingProgressKey implements Serializable {

    private Reader reader;

    private Book book;

    public ReadingProgressKey() {
    }

    public ReadingProgressKey(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
    }

    @ManyToOne
    public Reader getReader() {
        return reader;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingProgressKey that = (ReadingProgressKey) o;
        return Objects.equals(reader, that.reader) &&
                Objects.equals(book, that.book);
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader, book);
    }
}
