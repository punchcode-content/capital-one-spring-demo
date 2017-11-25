CREATE TABLE authors_books (
  author_id INTEGER NOT NULL,
  book_id   INTEGER NOT NULL,
  FOREIGN KEY (author_id) REFERENCES authors (id),
  FOREIGN KEY (book_id) REFERENCES books (id),
  PRIMARY KEY (author_id, book_id)
);