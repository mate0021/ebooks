package rnd.mate00.ebooks.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rnd.mate00.ebooks.commands.ReaderCommand;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.sec.SecureReaderRepository;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ReaderServiceIT {

    @Autowired
    private ReaderService subject;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private SecureReaderRepository secureReaderRepository;


    @Before
    public void setUp() {
        readerRepository.deleteAll();
        secureReaderRepository.deleteAll();
    }

    @Test
    public void shouldAddUserAndReader() {
        assertThat(readerRepository.count()).isEqualTo(0);
        assertThat(secureReaderRepository.count()).isEqualTo(0);

        ReaderCommand readerBean = new ReaderCommand(1, "name", "pass", "pass", "email@address");
        subject.saveReader(readerBean);

        assertThat(readerRepository.count()).isEqualTo(1);
        assertThat(secureReaderRepository.count()).isEqualTo(1);
    }

}