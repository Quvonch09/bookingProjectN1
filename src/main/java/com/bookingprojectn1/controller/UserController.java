package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.UserDTO;
import com.bookingprojectn1.security.CurrentUser;
import com.bookingprojectn1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Operation(summary = "Barcha userlar uzining profilini kurish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @GetMapping("/getMe")
    public ResponseEntity<ApiResponse> getMe(@CurrentUser User user){
        ApiResponse me = userService.getMe(user);
        return ResponseEntity.ok(me);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "Hamma uzini profili update qilish")
    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse> updateUser(@RequestParam(required = false) Long userId,
                                                  @RequestBody UserDTO user,
                                                  @CurrentUser User user1){
        ApiResponse apiResponse = userService.updateUser(userId, user, user1);
        return ResponseEntity.ok(apiResponse);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @Operation(summary = "ADMIN userlarni barchasini search qilish")
    @GetMapping("/searchUser")
    public ResponseEntity<ApiResponse> searchUsers(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "phoneNumber",required = false) String phoneNumber,
                                                   @RequestParam(value = "role") ERole role,
                                                   @RequestParam(value = "page" ,defaultValue = "0") int page,
                                                   @RequestParam(value = "size" ,defaultValue = "10") int size){
        ApiResponse apiResponse = userService.searchUsers(keyword, phoneNumber, role, page, size);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "User uzini profilini uchirish")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_LIBRARIAN','ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(@CurrentUser User user){
        ApiResponse apiResponse = userService.deleteUser(user);
        return ResponseEntity.ok(apiResponse);
    }
}
