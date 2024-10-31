package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private LibraryRepository libraryRepository;
    @InjectMocks
    private AdminService adminService;
    public ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void countAll() {

        when(userRepository.countByRole(ERole.ROLE_ADMIN)).thenReturn(1L);
        when(libraryRepository.count()).thenReturn(2L);
        when(userRepository.countByRole(ERole.ROLE_USER)).thenReturn(3L);

        ApiResponse apiResponse = adminService.countAll();
        assertNotNull(apiResponse);

        try {
            System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}