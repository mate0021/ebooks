package rnd.mate00.ebooks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.mate00.ebooks.commands.ReaderCommand;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.sec.Role;
import rnd.mate00.ebooks.sec.RoleRepository;
import rnd.mate00.ebooks.sec.SecureReader;
import rnd.mate00.ebooks.sec.SecureReaderRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReaderService {

    private static final Logger log = LoggerFactory.getLogger(ReaderService.class);

    private ReaderRepository readerRepository;

    private SecureReaderRepository secureReaderRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, SecureReaderRepository secureReaderRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.readerRepository = readerRepository;
        this.secureReaderRepository = secureReaderRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveReader(ReaderCommand readerCommand) {
        String username = readerCommand.getName();
        String password = passwordEncoder.encode(readerCommand.getPassword());

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByRoleName("reader").orElseThrow();
        roles.add(defaultRole);
        SecureReader secureReader = new SecureReader(username, password, true, roles);
        secureReaderRepository.save(secureReader);
        log.info("Saved reader {} to secured reader repository.", secureReader);

        Reader reader = new Reader(username);
        readerRepository.save(reader);
        log.info("Saved reader {} to reader repository.", reader);
    }
}
