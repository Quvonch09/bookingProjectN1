package com.bookingprojectn1.repository;


import com.bookingprojectn1.entity.Favourite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    @Modifying
    @Transactional
    @Query(value = "delete from book_favourite_list where favourite_list_id = ?1", nativeQuery = true)
    void deleteFavouriteBook(Integer favouriteListId);

    @Modifying
    @Transactional
    @Query(value = "delete from library_favourite_list where favourite_list_id = ?1", nativeQuery = true)
    void deleteFavouriteLibrary(Integer favouriteListId);

    @Query(value = "select * from book_favourite_list  bfl join favourite f on f.id = bfl.favourite_list_id where \n" +
            "            bfl.book_id = ?1 and f.created_by_id = ?2", nativeQuery = true)
    Optional<Favourite> findFavouriteBook(Long bookId, Long createdById);


    @Query(value = "select * from library_favourite_list lfl  join favourite f on lfl.favourite_list_id = f.id  where " +
            "lfl.library_id = ?1 and f.created_by_id = ?2", nativeQuery = true)
    Optional<Favourite> findFavouriteLibrary(Long libraryId, Long createdById);
}