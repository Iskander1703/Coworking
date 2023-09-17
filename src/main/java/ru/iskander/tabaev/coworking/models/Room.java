package ru.iskander.tabaev.coworking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer maxCapacity;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    private List<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "coworking_id", nullable = false)
    private Coworking coworking;
}
