CREATE TABLE Bookings
(
    id         SERIAL PRIMARY KEY,
    room_id    INT REFERENCES Rooms (id),
    start_time TIMESTAMP NOT NULL,
    end_time   TIMESTAMP NOT NULL,
    user_id    INT
);