package rnd.mate00.ebooks.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by mate00 on 11.02.18.
 */
@Embeddable
public class ShoppingKey implements Serializable {

    private Book book;

    private Reader reader;

    private Shop shop;

    public ShoppingKey() {
    }

    public ShoppingKey(Book book, Reader reader, Shop shop) {
        this.book = book;
        this.reader = reader;
        this.shop = shop;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @ManyToOne
    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @ManyToOne
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingKey that = (ShoppingKey) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(reader, that.reader) &&
                Objects.equals(shop, that.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, reader, shop);
    }

    @Override
    public String toString() {
        return "ShoppingKey{" +
                "book=" + book +
                ", reader=" + reader +
                ", shop=" + shop +
                '}';
    }
}
