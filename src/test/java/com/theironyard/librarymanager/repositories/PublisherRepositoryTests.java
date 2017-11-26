package com.theironyard.librarymanager.repositories;

import com.theironyard.librarymanager.entities.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PublisherRepositoryTests {
    @Autowired private PublisherRepository publisherRepo;

    @Test
    public void testIdAutomaticallyAssigned() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        Publisher savedPublisher = publisherRepo.save(publisher);

        assertNotNull(savedPublisher.getId());
    }

    @Test
    public void testCanRetrieveSavedPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = publisherRepo.save(publisher);

        Publisher retrievedPublisher = publisherRepo.findOne(publisher.getId());
        assertThat(retrievedPublisher, notNullValue());
        assertThat(retrievedPublisher.getId(), equalTo(publisher.getId()));
        assertThat(retrievedPublisher.getName(), equalTo("Publisher"));
    }

    @Test
    public void testFindAll() {
        List<Publisher> publishers = publisherRepo.findAll();
        assertNotNull(publishers);
        assertTrue(publishers.isEmpty());

        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisherRepo.save(publisher);

        publishers = publisherRepo.findAll();
        assertNotNull(publishers);
        assertTrue(!publishers.isEmpty());
    }

    @Test
    public void testCanUpdatePublisher() {
        // Set up -- save a new publisher
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = publisherRepo.save(publisher);
        publisher = publisherRepo.findOne(publisher.getId());

        publisher.setName("New name");
        publisher = publisherRepo.save(publisher);
        Publisher retrievedPublisher = publisherRepo.findOne(publisher.getId());
        assertThat(retrievedPublisher.getName(), equalTo("New name"));
    }

    @Test
    public void testCanDeletePublisher() {
        // Set up -- save a new publisher
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = publisherRepo.save(publisher);

        publisherRepo.delete(publisher.getId());
        Publisher retrievedPublisher = publisherRepo.findOne(publisher.getId());
        assertNull(retrievedPublisher);
    }
}
