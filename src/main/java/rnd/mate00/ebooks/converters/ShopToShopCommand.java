package rnd.mate00.ebooks.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.commands.ShopCommand;
import rnd.mate00.ebooks.model.Shop;

/**
 * Created by mate00 on 03.03.18.
 */
@Component
public class ShopToShopCommand implements Converter<Shop, ShopCommand> {

    @Override
    public ShopCommand convert(Shop source) {
        if (source == null) {
            return null;
        }

        return new ShopCommand(source.getId(), source.getName(), source.getUrl());
    }
}
