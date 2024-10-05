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
    @ManyToMany
    private List<FeedBackForLibrary> feedBackForLibraryList;
    @OneToOne
    private File file;
}
