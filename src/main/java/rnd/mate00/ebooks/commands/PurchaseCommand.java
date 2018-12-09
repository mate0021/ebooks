package rnd.mate00.ebooks.commands;

import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.Shop;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mate00 on 08.12.18.
 */
public class PurchaseCommand {

    private int bookId;
    private String title;
    private String author;
    private Reader reader;
    private BigDecimal price;
    private Date buyDate;
    private Shop shop;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return bookId == that.bookId &&
                Objects.equals(title, that.title) &&
                Objects.equals(author, that.author) &&
                Objects.equals(reader, that.reader) &&
                Objects.equals(price, that.price) &&
                Objects.equals(buyDate, that.buyDate) &&
                Objects.equals(shop, that.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, reader, price, buyDate, shop);
    }

    @Override
    public String toString() {
        return "PurchaseCommand{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", reader=" + reader +
                ", price=" + price +
                ", buyDate=" + buyDate +
                ", shop=" + shop +
                '}';
    }
}
