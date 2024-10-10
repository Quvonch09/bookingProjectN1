package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.BookReservation;
import com.bookingprojectn1.payload.BookReservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
    List<BookReservation> findByBookAndReservationDateBefore(Book book, LocalDate date);
}
