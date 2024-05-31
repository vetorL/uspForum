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

CREATE TABLE subject (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    abbreviation TEXT,
    code TEXT,
    related_course TEXT,
    teacher_id integer REFERENCES teacher (id),
    created_by_account_id integer REFERENCES account (id)
);