CREATE TABLE users (
  id                 ${AUTOINCREMENT} PRIMARY KEY,
  username           VARCHAR(255) NOT NULL UNIQUE,
  encrypted_password VARCHAR(255)
);

CREATE TABLE roles (
  id   ${AUTOINCREMENT} PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles_users (
  user_id INTEGER NOT NULL,
  role_id INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),
  PRIMARY KEY (user_id, role_id)
);