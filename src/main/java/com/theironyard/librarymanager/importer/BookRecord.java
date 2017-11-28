package com.theironyard.librarymanager.importer;

import com.opencsv.bean.CsvBindByName;

public class BookRecord {
    @CsvBindByName private String isbn;
    @CsvBindByName private String title;
    @CsvBindByName private String author;
    @CsvBindByName private String publisher;
    @CsvBindByName private Integer year_published;
    @CsvBindByName private String dewey_decimal;
    @CsvBindByName private String lcc_number;
    @CsvBindByName private String subjects;

    public String getIsbn() {
        return isbn.substring(0, 3) + "-" + isbn.substring(3);
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear_published() {
        return year_published;
    }

    public void setYear_published(Integer year_published) {
        this.year_published = year_published;
    }

    public String getDewey_decimal() {
        return dewey_decimal;
    }

    public void setDewey_decimal(String dewey_decimal) {
        this.dewey_decimal = dewey_decimal;
    }

    public String getLcc_number() {
        return lcc_number;
    }

    public void setLcc_number(String lcc_number) {
        this.lcc_number = lcc_number;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String toString() {
        return "BookRecord - " + this.getTitle() + " - " + this.getIsbn();
    }
}
