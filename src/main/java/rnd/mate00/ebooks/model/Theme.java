package rnd.mate00.ebooks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String theme;

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
