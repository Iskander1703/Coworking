package ru.iskander.tabaev.coworking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iskander.tabaev.coworking.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
