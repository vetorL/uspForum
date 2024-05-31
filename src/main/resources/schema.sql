CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    username TEXT,
    password TEXT
);

CREATE TABLE teacher (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT,
    teaches_at TEXT
);