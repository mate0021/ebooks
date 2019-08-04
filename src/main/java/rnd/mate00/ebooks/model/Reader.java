package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity(name = "reader")
public class Reader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "key.reader", cascade = CascadeType.ALL)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ReadingProgress> getReadingProgresses() {
        return readingProgresses;
    }

    public void setReadingProgresses(Set<ReadingProgress> readingProgresses) {
        this.readingProgresses = readingProgresses;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", readingProgresses=" + readingProgresses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id &&
                Objects.equals(name, reader.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
