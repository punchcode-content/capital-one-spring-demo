package com.theironyard.librarymanager.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Publisher {
    private Integer id;

    @NotNull
    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
