package ru.iskander.tabaev.coworking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "coworkings")
public class Coworking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "coworking")
    private List<Room> rooms;
}
