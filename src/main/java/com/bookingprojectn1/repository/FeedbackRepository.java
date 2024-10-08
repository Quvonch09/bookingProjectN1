package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query(value = "select * from book_feedback_list where book_id = ?1", nativeQuery = true)
    List<Feedback> findAllBookFeedbackList(Long bookId);



//    List<Feedback> findByBookId(Integer bookId);
}
