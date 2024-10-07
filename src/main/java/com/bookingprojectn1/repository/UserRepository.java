package com.bookingprojectn1.repository;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    int countByRoleAndEnabledTrue(ERole role);

    User findByIdAndRoleAndEnabledTrue(Long id, ERole role);

    List<User> findByRole(ERole role);


    @Query(value = """
                SELECT u.*
               FROM users u
               WHERE (:keyword IS NULL OR LOWER(u.first_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                      OR LOWER(u.last_name) LIKE LOWER(CONCAT('%', :keyword, '%')))
               AND (:phoneNumber IS NULL OR u.phone_number LIKE CONCAT('%', :phoneNumber, '%'))
               AND (u.role = :role)
                    """,
            nativeQuery = true)
    Page<User> searchUsers(@Param("keyword") String keyword,
                           @Param("phoneNumber") String phoneNumber,
                           @Param("role") String role,PageRequest pageRequest);

}
