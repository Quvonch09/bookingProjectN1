package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.PaymentDTO;
import com.bookingprojectn1.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lov qilish uchun")
    public ApiResponse savePayment(@RequestBody PaymentDTO paymentDTO){
        return paymentService.savePayment(paymentDTO);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovlarni barchasini kurish")
    public ApiResponse getAllPayments(){
        return paymentService.getPayments();
    }



    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni bittasini kurish")
    public ApiResponse getOnePayment(@PathVariable Long paymentId){
        return paymentService.getOnePayment(paymentId);
    }


    @PutMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni update qilish")
    public ApiResponse updatePayment(@PathVariable Long paymentId, @RequestBody PaymentDTO paymentDTO){
        return paymentService.updatePayment(paymentId, paymentDTO);
    }


    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni delete qilish")
    public ApiResponse deletePayment(@PathVariable Long paymentId){
        return paymentService.deletePayment(paymentId);
    }
}
