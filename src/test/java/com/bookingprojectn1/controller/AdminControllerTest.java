package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.res.ResAdmin;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
import com.bookingprojectn1.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminControllerTest {

    private ObjectMapper objectMapper;
    @Autowired
    private AdminController adminController;
    @MockBean
    private AdminService adminService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private LibraryRepository libraryRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"SUPER_ADMIN"})
    void countAll() {
        ResAdmin resAdmin = new ResAdmin(1L, 2L, 3L);
        ApiResponse apiResponse = new ApiResponse(resAdmin);

        when(userRepository.countByRoleAndEnabledTrue(ERole.ROLE_ADMIN)).thenReturn(1L);
        when(libraryRepository.count()).thenReturn(2L);
        when(userRepository.countByRoleAndEnabledTrue(ERole.ROLE_USER)).thenReturn(3L);
        when(adminService.countAll()).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = adminController.countAll();
        assertNotNull(response);
        System.out.println("Response: " + objectMapper.writeValueAsString(response.getBody()));
    }
}