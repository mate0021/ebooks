package rnd.mate00.ebooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.model.Reader;
import rnd.mate00.ebooks.model.ReadingProgress;
import rnd.mate00.ebooks.model.ReadingProgressKey;
import rnd.mate00.ebooks.repository.ReadingProgressRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Created by mate00 on 28.01.18.
 */
@Service
public class ReadingProgressService {

    private ReadingProgressRepository readingProgressRepository;

    @Autowired
    public ReadingProgressService(ReadingProgressRepository readingProgressRepository) {
        this.readingProgressRepository = readingProgressRepository;
    }

    public void startReadingBook(Book book, Reader reader) {
        startReadingBook(book, reader, LocalDate.now());
    }

    public void startReadingBook(Book book, Reader reader, LocalDate dateTime) {
        ReadingProgressKey key = new ReadingProgressKey(reader, book);

        readingProgressRepository
                .findById(key)
                .ifPresent(readingProgress -> {
                    throw new IllegalArgumentException("Can't start already started book");
                });

        Date startDate = Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        ReadingProgress progress = new ReadingProgress(key, startDate, null);
        readingProgressRepository.save(progress);
    }

    @Transactional
    public void stopReadingBook(Book book, Reader reader) {
        stopReadingBook(book, reader, LocalDate.now());
    }

    @Transactional
    public void stopReadingBook(Book book, Reader reader, LocalDate stopDate) {
        ReadingProgressKey key = new ReadingProgressKey(reader, book);
        Optional<ReadingProgress> byId = readingProgressRepository.findById(key);
        if (byId.isPresent()) {
            ReadingProgress progress = byId.get();
            if (progress.getEnd() != null) {
                    throw new IllegalArgumentException("Can't stop already stopped book");
            } else {
                Date endDate = Date.from(stopDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                readingProgressRepository.setEndDateForReadingProgress(endDate, book, reader);
            }
        }
    }
}
