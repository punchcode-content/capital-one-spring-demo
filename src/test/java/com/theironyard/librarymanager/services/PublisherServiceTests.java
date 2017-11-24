package com.theironyard.librarymanager.services;

import com.theironyard.librarymanager.entities.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PublisherServiceTests {
    @Autowired private PublisherService service;

    @Test
    public void testIdAutomaticallyAssigned() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        Publisher savedPublisher = service.saveOrUpdate(publisher);

        assertThat(savedPublisher, notNullValue());
    }

    @Test
    public void testCanRetrieveSavedPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = service.saveOrUpdate(publisher);

        Publisher retrievedPublisher = service.getById(publisher.getId());
        assertThat(retrievedPublisher, notNullValue());
        assertThat(retrievedPublisher.getId(), equalTo(publisher.getId()));
    }

    @Test
    public void testCanListAll() {
        Publisher publisher = new Publisher();
        publisher.setName("A Test Publisher");
        publisher = service.saveOrUpdate(publisher);

        List<Publisher> publishers = service.listAll();
        assertThat(publishers, hasItem(hasProperty("id", is(publisher.getId()))));
    }

    @Test
    public void testCanUpdatePublisher() {
        // Set up -- save a new publisher
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = service.saveOrUpdate(publisher);
        publisher = service.getById(publisher.getId());

        publisher.setName("New name");
        publisher = service.saveOrUpdate(publisher);
        Publisher retrievedPublisher = service.getById(publisher.getId());
        assertThat(retrievedPublisher.getName(), equalTo("New name"));
    }

    @Test
    public void testCanDeletePublisher() {
        // Set up -- save a new publisher
        Publisher publisher = new Publisher();
        publisher.setName("Publisher");
        publisher = service.saveOrUpdate(publisher);

        service.deleteById(publisher.getId());
        Publisher retrievedPublisher = service.getById(publisher.getId());
        assertThat(retrievedPublisher, nullValue());
    }
}
