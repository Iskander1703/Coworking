CREATE TABLE IF NOT EXISTS Rooms
(
    id           SERIAL PRIMARY KEY,
    coworking_id INT REFERENCES Coworkings (id),
    name         VARCHAR(255) NOT NULL,
    max_capacity INT          NOT NULL,
    description  TEXT
);