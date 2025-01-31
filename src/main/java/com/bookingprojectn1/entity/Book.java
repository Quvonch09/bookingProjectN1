package com.bookingprojectn1.entity;

import com.bookingprojectn1.entity.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String author;
    private Integer pageCount;
    private String year;
    @OneToOne
    private File pdf;
    @OneToOne
    private File bookImg;
    @ManyToMany
    private List<Feedback> feedbackList;
    @ManyToMany
    private List<Favourite> favouriteList;
    @ManyToOne
    private Library library;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @ManyToOne
    private Category category;
}
