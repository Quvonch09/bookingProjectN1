package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book b\n" +
            "            where (?1 IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', ?1, '%')))\n" +
            "            and (?3 IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', ?3, '%')))\n" +
            "and (?2 IS NULL OR LOWER(b.description) LIKE LOWER(CONCAT('%', ?2, '%')))\n" +
            "and (?5 IS NULL OR b.library_id = ?5) " +
            "and (?6 IS NULL OR b.category_id = ?6)" +
            "and (?7 IS NULL OR b.sub_category_id = ?7)" +
            "and (?8 IS NULL OR b.status = ?8)" +
            "and (?4 IS NULL OR b.year = ?4) ORDER BY b.id desc",nativeQuery = true)
    Page<Book> searchBook(String title, String description ,String author, String year,Long libraryId,
                          Long categoryId,Long subCategoryId,String role, Pageable pageable);


    @Query(value = "select b.* from book b join book_reservation br on b.id = br.book_id where" +
            " br.reservation_date =:local_date and br.user_id =:user_id ", nativeQuery = true)
    List<Book> findAllBook(@Param("local_date") LocalDate localDate,
                           @Param("user_id") Long user_id);

    boolean existsByTitleIgnoreCase(String title);
}
