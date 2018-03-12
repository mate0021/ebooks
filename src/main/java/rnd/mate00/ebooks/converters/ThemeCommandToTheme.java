package rnd.mate00.ebooks.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.commands.ThemeCommand;
import rnd.mate00.ebooks.model.Theme;

/**
 * Created by mate00 on 11.03.18.
 */
@Component
public class ThemeCommandToTheme implements Converter<ThemeCommand, Theme> {
    @Override
    public Theme convert(ThemeCommand source) {
        if (source == null) {
            return null;
        }

        return new Theme(source.getId(), source.getTheme());
    }
}
