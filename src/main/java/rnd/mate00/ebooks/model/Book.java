package rnd.mate00.ebooks.model;

import javax.persistence.*;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String author;

    private int locations;

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

    public Theme getTheme() {
        return theme;
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
