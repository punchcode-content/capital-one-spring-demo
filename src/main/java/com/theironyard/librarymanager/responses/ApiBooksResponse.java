package com.theironyard.librarymanager.responses;

import com.theironyard.librarymanager.entities.Book;
import org.springframework.data.domain.Page;

public class ApiBooksResponse {
    private Page<Book> payload;
    private String status;
    private String message;

    public ApiBooksResponse(Page<Book> payload) {
        this.status = "success";
        this.payload = payload;
    }

    public ApiBooksResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Page<Book> getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
