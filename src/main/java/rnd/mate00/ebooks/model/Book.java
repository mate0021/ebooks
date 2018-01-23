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
}
