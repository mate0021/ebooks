package rnd.mate00.ebooks.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Created by mate00 on 21.01.18.
 */
@Entity(name = "shopping")
@AssociationOverrides({
        @AssociationOverride(name = "shoppingKey.reader", joinColumns = @JoinColumn(name = "key.reader")),
        @AssociationOverride(name = "shoppingKey.book", joinColumns = @JoinColumn(name = "key.book")),
        @AssociationOverride(name = "shoppingKey.shop", joinColumns = @JoinColumn(name = "key.shop"))
})
public class Shopping implements Serializable {

    private ShoppingKey shoppingKey;

    private Date buyDate;

    private BigDecimal price;

    public Shopping() {
    }

    @EmbeddedId
    public ShoppingKey getShoppingKey() {
        return shoppingKey;
    }

    public void setShoppingKey(ShoppingKey shoppingKey) {
        this.shoppingKey = shoppingKey;
    }

    @Transient
    public Book getBook() {
        return shoppingKey.getBook();
    }

    @Transient
    public Reader getReader() {
        return shoppingKey.getReader();
    }

    @Transient
    public Shop getShop() {
        return shoppingKey.getShop();
    }

    public void setBook(Book book) {
        shoppingKey.setBook(book);
    }

    public void setReader(Reader reader) {
        shoppingKey.setReader(reader);
    }

    public void setShop(Shop shop) {
        shoppingKey.setShop(shop);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "buy_date")
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shopping shopping = (Shopping) o;
        return Objects.equals(shoppingKey, shopping.shoppingKey) &&
                Objects.equals(buyDate, shopping.buyDate) &&
                Objects.equals(price, shopping.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingKey, buyDate, price);
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "shoppingKey=" + shoppingKey +
                ", buyDate=" + buyDate +
                ", price=" + price +
                '}';
    }
}
