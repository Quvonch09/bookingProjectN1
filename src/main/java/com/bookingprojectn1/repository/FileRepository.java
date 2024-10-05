package com.bookingprojectn1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingprojectn1.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
