package rnd.mate00.ebooks.commands;

import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shop;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mate00 on 08.12.18.
 */
public class PurchaseCommand {

    private Book book;

    private Reader reader;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @NotNull
    private Date buyDate;

    private Shop shop;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

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
        PurchaseCommand that = (PurchaseCommand) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(reader, that.reader) &&
                Objects.equals(price, that.price) &&
                Objects.equals(buyDate, that.buyDate) &&
                Objects.equals(shop, that.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, reader, price, buyDate, shop);
    }

    @Override
    public String toString() {
        return "PurchaseCommand{" +
                "book=" + book +
                ", reader=" + reader +
                ", price=" + price +
                ", buyDate=" + buyDate +
                ", shop=" + shop +
                '}';
    }
}
