package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    boolean existsByNameIgnoreCase(String name);

    @Query(value = "select * from library l where (?1 IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', ?1, '%')))" , nativeQuery = true)
    Page<Library> searchLibrary(String name, Pageable pageable);
    Long countByOwner(User owner);
}
