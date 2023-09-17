package ru.iskander.tabaev.coworking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iskander.tabaev.coworking.models.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByCapacityGreaterThanEqualAndCoworkingId(Integer capacity, Long coworkingId);
    Optional<Room> findByCoworkingIdAndId(Long coworkingId, Long roomId);

    @Query("SELECT r FROM Room r " +
            "WHERE r.coworking.id = :coworkingId " +
            "AND r.capacity >= :minCapacity " +
            "AND NOT EXISTS (" +
            "  SELECT b FROM Booking b " +
            "  WHERE b.room.id = r.id " +
            "  AND (b.startTime < :endDateTime AND b.endTime > :startDateTime)" +
            ")")
    List<Room> findAvailableRoomsInCoworking(
            @Param("coworkingId") Long coworkingId,
            @Param("minCapacity") Integer minCapacity,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );
}
