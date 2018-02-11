package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
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

    public Theme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                '}';
    }
}
