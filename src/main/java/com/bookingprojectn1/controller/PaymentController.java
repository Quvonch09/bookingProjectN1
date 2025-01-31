package com.bookingprojectn1.controller;

import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.PaymentDTO;
import com.bookingprojectn1.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> savePayment(@RequestBody PaymentDTO paymentDTO){
        ApiResponse apiResponse = paymentService.savePayment(paymentDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovlarni barchasini kurish")
    public ResponseEntity<ApiResponse> getAllPayments(){
        ApiResponse payments = paymentService.getPayments();
        return ResponseEntity.ok(payments);
    }



    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni bittasini kurish")
    public ResponseEntity<ApiResponse> getOnePayment(@PathVariable Long paymentId){
        ApiResponse onePayment = paymentService.getOnePayment(paymentId);
        return ResponseEntity.ok(onePayment);
    }


    @PutMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni update qilish")
    public ResponseEntity<ApiResponse> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentDTO paymentDTO){
        ApiResponse apiResponse = paymentService.updatePayment(paymentId, paymentDTO);
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "To'lovni delete qilish")
    public ResponseEntity<ApiResponse> deletePayment(@PathVariable Long paymentId){
        ApiResponse apiResponse = paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(apiResponse);
    }
}
