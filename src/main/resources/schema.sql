DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    username TEXT,
    password TEXT
);

CREATE TABLE IF NOT EXISTS subject (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    abbreviation TEXT,
    code TEXT,
    related_course TEXT,
    teacher_name TEXT,
    created_by_account_id integer REFERENCES account (id)
);