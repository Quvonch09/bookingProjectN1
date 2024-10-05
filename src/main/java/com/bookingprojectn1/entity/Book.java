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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Integer pageCount;
    @OneToOne
    private File file;
    @ManyToMany
    private List<FeedBackForBook> feedBackForBook;
}
