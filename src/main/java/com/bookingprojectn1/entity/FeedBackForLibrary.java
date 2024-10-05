package com.bookingprojectn1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackForLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private int ball; // 1-5 gacha ball kitobga
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Library library;

}
