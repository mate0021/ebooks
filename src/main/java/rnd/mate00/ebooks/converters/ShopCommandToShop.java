package rnd.mate00.ebooks.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.commands.ShopCommand;
import rnd.mate00.ebooks.model.Shop;

/**
 * Created by mate00 on 03.03.18.
 */
@Component
public class ShopCommandToShop implements Converter<ShopCommand, Shop> {
    @Override
    public Shop convert(ShopCommand source) {
        if (source == null) {
            return null;
        }

        return new Shop(source.getId(), source.getName(), source.getUrl());
    }
}
