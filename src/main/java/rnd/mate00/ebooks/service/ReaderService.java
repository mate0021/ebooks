package rnd.mate00.ebooks.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rnd.mate00.ebooks.commands.ReaderCommand;
import rnd.mate00.ebooks.repository.ReaderRepository;
import rnd.mate00.ebooks.repository.SecureReaderRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ReaderService {

    private ReaderRepository readerRepository;

    private SecureReaderRepository secureReaderRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public void saveReader(ReaderCommand readerCommand) {
        String username = readerCommand.getName();
        List<GrantedAuthority> authorities = Collections.emptyList();
        String password = passwordEncoder.encode(readerCommand.getPassword());

        User user = new User(username, password, authorities);
        secureReaderRepository.save(user);
    }
}
