CREATE TABLE books (
  id             ${autoincrement} PRIMARY KEY,
  title          VARCHAR(255) NOT NULL,
  isbn           CHAR(14),
  year_published INTEGER,
  publisher_id   INTEGER,
  FOREIGN KEY (publisher_id) REFERENCES publishers (id)
);