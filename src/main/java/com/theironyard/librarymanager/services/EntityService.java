package com.theironyard.librarymanager.services;

import java.util.List;

public interface EntityService<T> {
    List<T> listAll();

    T getById(Integer id);

    T saveOrUpdate(T author);

    void deleteById(Integer id);
}
