package rnd.mate00.ebooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.repository.ShopRepository;

/**
 * Created by mate00 on 05.03.18.
 */
@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ShopRepository shopRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Shop ebookPoint = new Shop("Ebookpoint", "www.ebookpoint.pl");
        Shop virtualo = new Shop("Virtualo", "www.virtualo.pl");
        Shop publio = new Shop("Publio", "www.publio.pl");

        shopRepository.save(ebookPoint);
        shopRepository.save(virtualo);
        shopRepository.save(publio);

        System.out.println(String.format("Added %d shops.", shopRepository.count()));
    }
}
