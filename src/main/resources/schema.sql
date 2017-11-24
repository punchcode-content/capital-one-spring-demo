CREATE TABLE authors (
  id   IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE publishers (
  id   IDENTITY PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
  id             IDENTITY PRIMARY KEY,
  title          VARCHAR(255) NOT NULL,
  isbn           CHAR(14),
  year_published INTEGER,
  publisher_id   INTEGER,
  FOREIGN KEY (publisher_id) REFERENCES publishers (id)
);

CREATE TABLE authors_books (
  author_id INTEGER NOT NULL,
  book_id   INTEGER NOT NULL,
  FOREIGN KEY (author_id) REFERENCES authors (id),
  FOREIGN KEY (book_id) REFERENCES books (id),
  PRIMARY KEY (author_id, book_id)
);