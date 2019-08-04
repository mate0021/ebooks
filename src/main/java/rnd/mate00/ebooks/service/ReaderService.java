package rnd.mate00.ebooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    public void saveReader(ReaderCommand readerCommand) {
        String username = readerCommand.getName();
        String password = passwordEncoder.encode(readerCommand.getPassword());

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByRoleName("reader").orElseThrow();
        roles.add(defaultRole);
        SecureReader secureReader = new SecureReader(username, password, true, roles);
        secureReaderRepository.save(secureReader);

        Reader reader = new Reader(username);
        readerRepository.save(reader);
    }
}
