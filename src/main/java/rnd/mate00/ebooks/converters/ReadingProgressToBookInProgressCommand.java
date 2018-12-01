package rnd.mate00.ebooks.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.commands.BookInProgressCommand;
import rnd.mate00.ebooks.model.ReadingProgress;

/**
 * Created by mate00 on 26.11.18.
 */
@Component
public class ReadingProgressToBookInProgressCommand implements Converter<ReadingProgress, BookInProgressCommand> {

    @Override
    public BookInProgressCommand convert(ReadingProgress source) {
        return new BookInProgressCommand(source.getBook(), source.getStart(), source.getEnd());
    }
}
