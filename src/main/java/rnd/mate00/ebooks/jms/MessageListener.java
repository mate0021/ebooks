package rnd.mate00.ebooks.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import rnd.mate00.ebooks.repository.ThemeRepository;

@Component
public class MessageListener {

    @Autowired
    private ThemeRepository themeRepository;


    @JmsListener(destination = "ebook.queue")
    public void onMessage(IncomingMessage message) {
        System.out.println("got message " + message);

    }
}
