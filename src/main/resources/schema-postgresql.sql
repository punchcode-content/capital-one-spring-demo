DROP TABLE IF EXISTS authors_books;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS publishers;

CREATE TABLE authors (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE publishers (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
  id             SERIAL PRIMARY KEY,
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