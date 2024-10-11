package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Followed;
import com.bookingprojectn1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowedRepository extends JpaRepository<Followed, Integer> {

        @Query(value = "select * from library_followed_list where library_id =?1",nativeQuery = true)
        List<Followed> findAll(Long libraryId);

        @Query(value = "select * from followed left join library_followed_list lfl where" +
                " user_id =?1 and lfl.library_id =?2",nativeQuery = true)
        List<Followed> findAllFollowed(Long userId, Long libraryId);
}
