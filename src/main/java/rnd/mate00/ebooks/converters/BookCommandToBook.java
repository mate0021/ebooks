package rnd.mate00.ebooks.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.commands.BookCommand;
import rnd.mate00.ebooks.model.Book;

/**
 * Created by mate00 on 17.03.18.
 */
@Component
public class BookCommandToBook implements Converter<BookCommand, Book> {

    @Override
    public Book convert(BookCommand source) {
        if (source == null) {
            return null;
        }

        return new Book(source.getId(), source.getTitle(), source.getAuthor(), source.getLocations(), source.getTheme());
    }
}
