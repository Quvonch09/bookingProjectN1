package com.bookingprojectn1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double lat;
    private double lng;
    @ManyToOne
    private User owner;

    @ManyToMany
    private List<Feedback> feedbackList;

    @ManyToMany
    private List<Favourite> favouriteList;

    @ManyToMany
    private List<Followed> followedList;

    @OneToOne
    private File file;
}
