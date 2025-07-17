package ru.practicum.shareit.request;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests", schema = "public")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "requestor_id")
    private Long requestor;

    private LocalDateTime created = LocalDateTime.now();
}
