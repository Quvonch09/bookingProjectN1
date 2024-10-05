package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.payload.req.ReqBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book b\n" +
            "            where (?1 IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', ?1, '%')))\n" +
            "            and (?2 IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', ?2, '%')))\n" +
            "and (?3 IS NULL OR b.library_id = ?3)",nativeQuery = true)
    Page<Book> searchBook(String title, String author,Long libraryId, Pageable pageable);
}
