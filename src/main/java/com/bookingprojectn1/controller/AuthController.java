package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.auth.AuthLogin;
import com.bookingprojectn1.payload.auth.AuthRegister;
import com.bookingprojectn1.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> logIn(@Valid @RequestBody AuthLogin authLogin){
        return ResponseEntity.ok(authService.login(authLogin));
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody AuthRegister authRegister){
        return ResponseEntity.ok(authService.register(authRegister));
    }


    @Operation(summary = "Parolni update qilish")
    @PutMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody AuthLogin authLogin){
        ApiResponse apiResponse = authService.forgotPassword(authLogin);
        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Admin yangi user qoshadi")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/admin/save-user")
    public ResponseEntity<ApiResponse> adminSaveTeacher(@Valid @RequestBody AuthRegister auth,
                                                        @RequestParam ERole eRole){
        return ResponseEntity.ok(authService.adminSaveLibrarian(auth,eRole));
    }

}
