package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.IdList;
import com.bookingprojectn1.payload.res.ResNotification;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@CrossOrigin
public class NotificationController {

    private final NotificationService notificationService;


    @PreAuthorize("hasAnyRole('ROLE_MASTER','ROLE_USER','ROLE_BARBER', 'ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Barcha roldagilar oziga kelgan bildirishnomalarni koradi, admin ham")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllNotifications(@CurrentUser User user) {
        return ResponseEntity.ok(notificationService.getNotifications(user));
    }

    @PreAuthorize("hasAnyRole('ROLE_MASTER','ROLE_USER','ROLE_BARBER', 'ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Barcha roldagilar oziga kelgan bildirishnomalarni sonini koradi")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countNotifications(@CurrentUser User user) {
        return ResponseEntity.ok(notificationService.getCountNotification(user));
    }

    @PreAuthorize("hasAnyRole('ROLE_MASTER','ROLE_USER','ROLE_BARBER', 'ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Barcha roldagilar oziga kelgan bildirishnomalarni oqilgan qilishi")
    @PostMapping("/read")
    public ResponseEntity<ApiResponse> readNotifications(@RequestBody IdList idList) {
        return ResponseEntity.ok(notificationService.isReadAllNotification(idList));
    }


    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @Operation(summary = "Admin hammaga notification jonatishi")
    @PostMapping("/send/all-users")
    public ResponseEntity<ApiResponse> sendAllUsersNotification(@RequestBody ResNotification resNotification,
                                                                @RequestParam(required = false, defaultValue = "0")
                                                                Long fileId) {
        return ResponseEntity.ok(notificationService.adminSendNotification(resNotification, fileId));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @Operation(summary = "faqat admin uchun")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> sendAllUsersNotification(@RequestParam Long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }


//    @Operation(summary = "asosiy saytdagi kontact qismida ishlatiladi, data ichida contact=true chiqsa rangini boshqacha qilingla")
//    @PostMapping("/contact")
//    public ResponseEntity<ApiResponse> contactNotification(@RequestBody ResContactNotification contact) {
//        return ResponseEntity.ok(notificationService.contactNotification(contact));
//    }
//
//
//    @PostMapping("/registrant")
//    public ResponseEntity<ApiResponse> notification(@CurrentUser User user) {
//        return ResponseEntity.ok(notificationService.notification(user));
//    }


}


