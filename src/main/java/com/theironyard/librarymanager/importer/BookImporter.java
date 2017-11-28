package com.theironyard.librarymanager.importer;

import com.opencsv.bean.CsvToBeanBuilder;
import com.theironyard.librarymanager.entities.Author;
import com.theironyard.librarymanager.entities.Book;
import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.repositories.AuthorRepository;
import com.theironyard.librarymanager.repositories.BookRepository;
import com.theironyard.librarymanager.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class BookImporter {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    private void saveRecord(BookRecord record) {
        Book book = new Book();
        book.setTitle(record.getTitle());
        book.setIsbn(record.getIsbn());
        book.setYearPublished(record.getYear_published());

        String[] authors = record.getAuthor().split("\\|");
        for (String name : authors) {
            Author author = authorRepository.findOneByName(name);
            if (author == null) {
                author = new Author();
                author.setName(name);
                author = authorRepository.save(author);
            }
            book.addAuthor(author);
        }

        Publisher publisher = publisherRepository.findOneByName(record.getPublisher());
        if (publisher == null) {
            publisher = new Publisher();
            publisher.setName(record.getPublisher());
            publisher = publisherRepository.save(publisher);
        }
        book.setPublisher(publisher);

        bookRepository.save(book);
    }

    public void runImport() {
        try {
            File csvFile = new ClassPathResource("verified.csv").getFile();
            FileReader fr = new FileReader(csvFile);

            List<BookRecord> bookRecords = new CsvToBeanBuilder<BookRecord>(fr)
                    .withType(BookRecord.class).withSeparator('\t').build().parse();

            for (BookRecord record : bookRecords) {
                saveRecord(record);
            }

            System.out.println(bookRecords.size() + " records imported");
        } catch (IOException ex) {
            System.out.println("Could not find file to import books");
        }
    }
}
