DROP TABLE IF EXISTS items;

CREATE TABLE items
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);