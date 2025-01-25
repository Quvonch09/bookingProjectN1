package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.Payment;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.PayType;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.PaymentDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.PaymentRepository;
import com.bookingprojectn1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    public ApiResponse savePayment(PaymentDTO paymentDTO)
    {
        User user = userRepository.findById(paymentDTO.getPayerId()).orElse(null);
        if (user == null){
            return new ApiResponse(ResponseError.NOTFOUND("User"));
        }

        Library library = libraryRepository.findById(paymentDTO.getLibraryId()).orElse(null);

        Payment payment = Payment.builder()
                .payer(user)
                .payType(PayType.valueOf(paymentDTO.getPayType()))
                .paySum(paymentDTO.getPaySum())
                .library(library)
                .payDate(paymentDTO.getPayDate())
                .createdAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        return new ApiResponse("Payment successfully saved");
    }


    public ApiResponse getPayments()
    {
        List<PaymentDTO> paymentDTOList = new ArrayList<>();
        for (Payment payment : paymentRepository.findAll()) {
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .id(payment.getId())
                    .paySum(payment.getPaySum())
                    .payDate(payment.getPayDate())
                    .createDate(payment.getCreatedAt())
                    .updateDate(payment.getUpdatedAt())
                    .payerId(payment.getPayer().getId())
                    .payType(payment.getPayType().name())
                    .libraryId(payment.getLibrary().getId())
                    .build();
            paymentDTOList.add(paymentDTO);
        }

        return new ApiResponse(paymentDTOList);
    }


    public ApiResponse getOnePayment(Long paymentId)
    {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Payment"));
        }

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(payment.getId())
                .paySum(payment.getPaySum())
                .payDate(payment.getPayDate())
                .createDate(payment.getCreatedAt())
                .updateDate(payment.getUpdatedAt())
                .payerId(payment.getPayer().getId())
                .payType(payment.getPayType().name())
                .libraryId(payment.getLibrary().getId())
                .build();

        return new ApiResponse(paymentDTO);
    }


    public ApiResponse updatePayment(Long paymentId, PaymentDTO paymentDTO) {

        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Payment"));
        }

        User user = userRepository.findById(paymentDTO.getPayerId()).orElse(null);
        if (user == null) {
            return new ApiResponse(ResponseError.NOTFOUND("User"));
        }

        payment.setPaySum(paymentDTO.getPaySum());
        payment.setPayDate(paymentDTO.getPayDate());
        payment.setUpdatedAt(LocalDateTime.now());
        payment.setPayer(user);
        payment.setPayType(PayType.valueOf(paymentDTO.getPayType()));
        paymentRepository.save(payment);
        return new ApiResponse("Payment successfully updated");
    }


    public ApiResponse deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Payment"));
        }
        paymentRepository.delete(payment);
        return new ApiResponse("Payment successfully deleted");
    }
}
