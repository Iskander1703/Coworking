CREATE TABLE Coworkings
(
    id          serial PRIMARY KEY,
    name        varchar(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    description TEXT
);