package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Theme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String theme;

    @OneToMany
    private Set<Book> books;


    public Theme() {
    }

    public Theme(int id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public Theme(String theme) {
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme1 = (Theme) o;
        return Objects.equals(theme, theme1.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme);
    }
}
