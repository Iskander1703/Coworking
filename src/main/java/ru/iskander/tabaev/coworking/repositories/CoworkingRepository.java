package ru.iskander.tabaev.coworking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iskander.tabaev.coworking.models.Coworking;

@Repository
public interface CoworkingRepository extends JpaRepository<Coworking, Long> {
}
