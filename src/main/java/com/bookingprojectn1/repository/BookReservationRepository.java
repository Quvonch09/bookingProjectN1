package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    @Query(value = "select br.* from book_reservation br where br.book_id =:bookId " +
            "and br.user_id =:userId and br.end_reservation >:date ", nativeQuery = true)
    List<BookReservation> findByBookAndUserAndEndReservationBefore( @Param("bookId") Long book,
                                                                    @Param("userId") Long user,
                                                                    @Param("date") LocalDate date);

    @Query(value = "select br.* from book_reservation br where br.user_id =:user_id and br.end_reservation =:date ",nativeQuery = true)
    List<BookReservation> findAllByUser(@Param("user_id") Long id, @Param("date") LocalDate date);
}
