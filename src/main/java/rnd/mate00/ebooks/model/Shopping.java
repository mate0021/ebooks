package rnd.mate00.ebooks.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mate00 on 21.01.18.
 */
//@Entity
public class Shopping {

    // compound key?
    private Reader reader;

    private Book book;

    private Shop shop;

    private Date buyDate;

    private BigDecimal price;
}
