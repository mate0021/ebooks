package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String author;

    private int locations;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "key.book")
    private Set<ReadingProgress> readingProgresses = new HashSet<>();

    @OneToOne
    private Theme theme;

    public Book() {
    }

    public Book(String title, String author, int locations, Theme theme) {
        this.title = title;
        this.author = author;
        this.locations = locations;
        this.theme = theme;
    }

    public Book(int id, String title, String author, int locations, Theme theme) {
        this(title, author, locations, theme);
        this.id = id;
    }

    public Book(int id, String title, String author, int locations, Set<ReadingProgress> readingProgresses, Theme theme) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.locations = locations;
        this.readingProgresses = readingProgresses;
        this.theme = theme;
    }

    public Theme getTheme() {
        return new Theme("x");
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", locations=" + locations +
                ", theme=" + theme +
                '}';
    }
}
