package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity(name = "reading_progress")
@AssociationOverrides({
        @AssociationOverride(name = "key.book", joinColumns = @JoinColumn(name = "key_book")),
        @AssociationOverride(name = "key.reader", joinColumns = @JoinColumn(name = "key_reader"))
})
public class ReadingProgress implements Serializable {

    private ReadingProgressKey key;

    private Date start;

    private Date end;

    @EmbeddedId
    public ReadingProgressKey getKey() {
        return key;
    }

    public void setKey(ReadingProgressKey key) {
        this.key = key;
    }

    public ReadingProgress() {}

    public ReadingProgress(ReadingProgressKey key, Date start, Date end) {
        this.key = key;
        this.start = start;
        this.end = end;
    }

    public ReadingProgress(ReadingProgressKey key, LocalDate start, LocalDate end) {
        this(key, from(start.atStartOfDay(systemDefault()).toInstant()), from(end.atStartOfDay(systemDefault()).toInstant()));
    }

    @Transient
    public Book getBook() {
        return key.getBook();
    }

    @Transient
    public Reader getReader() {
        return key.getReader();
    }

    public void setBook(Book book) {
        key.setBook(book);
    }

    public void setReader(Reader reader) {
        key.setReader(reader);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "reading_start", nullable = false)
    public Date getStart() {
        return start;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "reading_end")
    public Date getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ReadingProgress{" +
                "key=" + key +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingProgress progress = (ReadingProgress) o;
        return Objects.equals(key, progress.key) &&
                Objects.equals(start, progress.start) &&
                Objects.equals(end, progress.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, start, end);
    }
}

