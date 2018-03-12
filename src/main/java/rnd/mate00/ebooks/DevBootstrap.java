package rnd.mate00.ebooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.model.Shop;
import rnd.mate00.ebooks.model.Theme;
import rnd.mate00.ebooks.repository.ShopRepository;
import rnd.mate00.ebooks.repository.ThemeRepository;

/**
 * Created by mate00 on 05.03.18.
 */
@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addShops();
        addThemes();
    }

    private void addShops() {
        Shop ebookPoint = new Shop("Ebookpoint", "www.ebookpoint.pl");
        Shop virtualo = new Shop("Virtualo", "www.virtualo.pl");
        Shop publio = new Shop("Publio", "www.publio.pl");

        shopRepository.save(ebookPoint);
        shopRepository.save(virtualo);
        shopRepository.save(publio);

        System.out.println(String.format("Added %d shops.", shopRepository.count()));
    }

    private void addThemes() {
        Theme fact = new Theme("Reportaż");
        Theme historical = new Theme("Historyczna");
        Theme criminal = new Theme("Kryminał");
        Theme sensation = new Theme("Sensacja");

        themeRepository.save(fact);
        themeRepository.save(historical);
        themeRepository.save(criminal);
        themeRepository.save(sensation);

        System.out.println(String.format("Added %s themes.", themeRepository.count()));
    }
}
