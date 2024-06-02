DROP VIEW subject_with_teacher_name;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    username TEXT,
    password TEXT
);

CREATE TABLE IF NOT EXISTS teacher (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT,
    teaches_at TEXT
);

CREATE TABLE IF NOT EXISTS subject (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    abbreviation TEXT,
    code TEXT,
    related_course TEXT,
    teacher_id integer REFERENCES teacher (id),
    created_by_account_id integer REFERENCES account (id)
);