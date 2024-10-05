package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.UserBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserBooksRepository extends JpaRepository<UserBooks, Long> {
    boolean existsByBookIdAndUsername(Long bookId, String userName);

    @Query(value = "select * from user_books ub where (?1 IS NULL OR ub.book_id = ?1) " +
            "and (?1 IS NULL OR LOWER(ub.user_name) LIKE LOWER(CONCAT('%', ?1, '%')))", nativeQuery = true)
    Page<UserBooks> searchUserBooks(Long bookId, String username, Pageable pageable);
}
