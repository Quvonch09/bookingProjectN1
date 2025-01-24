package com.bookingprojectn1.repository;


import com.bookingprojectn1.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
