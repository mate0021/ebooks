package rnd.mate00.ebooks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity
public class Reader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public Reader() {
    }

    public Reader(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
