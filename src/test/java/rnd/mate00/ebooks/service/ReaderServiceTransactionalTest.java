package rnd.mate00.ebooks.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rnd.mate00.ebooks.commands.ReaderCommand;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.sec.Role;
import rnd.mate00.ebooks.sec.RoleRepository;
import rnd.mate00.ebooks.sec.SecureReaderRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@ActiveProfiles("test")
//@Import({BasicSecurityConfiguration.class, SecurityTestDatabaseConfig.class})
public class ReaderServiceTransactionalTest {

    @Autowired
    private ReaderService subject;

    @MockBean
    private ReaderRepository readerRepository;

    @Autowired
    private SecureReaderRepository secureReaderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        clearRepositories();
        Role role = roleRepository.save(new Role(1, "reader"));
    }

    @Test
    public void t() {
        // given
        ReaderCommand readerBean = new ReaderCommand(1, "reader", "pass123", "pass123", "e@mail");
        when(readerRepository.save(any(Reader.class))).thenThrow(new IllegalStateException("Something wrong with db"));
        assertThat(secureReaderRepository.count()).isEqualTo(0);


        // when
        try {
            subject.saveReader(readerBean);
        } catch (Exception e) {
            //
        }

        // then
        assertThat(secureReaderRepository.count()).isEqualTo(0);
    }

    private void clearRepositories() {
        // clear what DevBootstrap added
        secureReaderRepository.deleteAll();
        roleRepository.deleteAll();
        readerRepository.deleteAll();
    }
}