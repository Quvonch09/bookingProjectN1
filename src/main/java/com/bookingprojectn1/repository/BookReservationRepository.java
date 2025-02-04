package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.BookOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookOrder, Long> {

    @Query(value = "select br.* from book_order br where br.book_id =:bookId " +
            "and br.user_id =:userId and br.end_reservation >:date ", nativeQuery = true)
    List<BookOrder> findByBookAndUserAndEndReservationBefore(@Param("bookId") Long book,
                                                             @Param("userId") Long user,
                                                             @Param("date") LocalDate date);

    @Query(value = "select br.* from book_order br where br.user_id =:user_id and br.end_reservation =:date ",nativeQuery = true)
    List<BookOrder> findAllByUser(@Param("user_id") Long id, @Param("date") LocalDate date);

    @Query(value = """
            select b.* from book_order b 
            where (?1 IS NULL OR b.book_id = ?1)  
            and (?2 IS NULL OR b.user_id = ?2) 
            and (?3 IS NULL OR b.start_reservation = ?3) 
            and (?4 IS NULL OR b.end_reservation = ?4)""",nativeQuery = true)
    Page<BookOrder> findAll(Long book, Long user, LocalDate startReservation, LocalDate endReservation, Pageable pageable);

//    Page<BookOrder> findAll(LocalDate startDate,LocalDate endDate,Long userId,Long bookId,Pageable pageable);
}
