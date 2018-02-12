package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Reader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "key.reader", cascade = CascadeType.ALL)
    private Set<ReadingProgress> readingProgresses = new HashSet<>();

    public Reader() {
    }

    public Reader(String name) {
        this.name = name;
    }

    public Reader(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reader(int id, String name, Set<ReadingProgress> readingProgresses) {
        this.id = id;
        this.name = name;
        this.readingProgresses = readingProgresses;
    }
}
