package com.theironyard.librarymanager.services.map;

import com.theironyard.librarymanager.entities.Publisher;
import com.theironyard.librarymanager.services.PublisherService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapPublisherService implements PublisherService {
    private Map<Integer, Publisher> publishers;

    public MapPublisherService() {
        publishers = new HashMap<>();
    }

    @Override
    public List<Publisher> listAll() {
        return new ArrayList<>(publishers.values());
    }

    @Override
    public Publisher getById(Integer id) {
        return publishers.get(id);
    }

    @Override
    public Publisher saveOrUpdate(Publisher publisher) {
        if (publisher != null) {
            if (publisher.getId() == null) {
                publisher.setId(getNextKey());
            }
            publishers.put(publisher.getId(), publisher);

            return publisher;
        } else {
            throw new RuntimeException("Publisher cannot be null");
        }
    }

    @Override
    public void deleteById(Integer id) {
        Publisher publisher = publishers.get(id);
        publishers.remove(id);
    }

    private Integer getNextKey() {
        if (publishers.keySet().isEmpty()) {
            return 1;
        } else {
            return Collections.max(publishers.keySet()) + 1;
        }
    }
}

