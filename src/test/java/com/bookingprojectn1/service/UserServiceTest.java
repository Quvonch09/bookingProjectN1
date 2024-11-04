package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.UserDTO;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.UserRepository;
import com.bookingprojectn1.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private JwtProvider jwtProvider;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    void getMe() {
        User user = new User(
                2L,
                "Quvonchbek",
                "Bobomurodov",
                "quvonch09",
                "998905173977",
                ERole.ROLE_LIBRARIAN,
                "root123",
                new File(),
                true,
                true,
                true,
                true
        );

        ApiResponse me = userService.getMe(user);
        assertNotNull(me);
        System.out.println(objectMapper.writeValueAsString(me));
    }

    @Test
    @SneakyThrows
    void updateUser() {
        Long userId = 2L;
        UserDTO userDTO = new UserDTO(
                2L,
                "Quvonchbek",
                "Bobomurodov",
                "Quvonch09",
                "998905173977",
                ERole.ROLE_LIBRARIAN.name(),
                1L
        );
        User user = new User(
                2L,
                "Quvonchbek",
                "Bobomurodov",
                "quvonch09",
                "998905173977",
                ERole.ROLE_LIBRARIAN,
                "root123",
                new File(),
                true,
                true,
                true,
                true
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(fileRepository.findById(user.getFile().getId())).thenReturn(Optional.of(new File()));
        when(userRepository.save(user)).thenReturn(user);
        when(jwtProvider.generateToken(user.getPhoneNumber())).thenReturn("Bearer token");
        ApiResponse updateUser = userService.updateUser(userId, userDTO);
        assertNotNull(updateUser);
        System.out.println("Response: " + objectMapper.writeValueAsString(updateUser));
    }

    @Test
    @SneakyThrows
    void searchUsers() {
        String keyword = "Quvonchbek";
        String phoneNumber = "998905173977";
        int page = 0;
        int size = 10;
        List<User> users = new ArrayList<>();
        User user = new User(
                2L,
                "Quvonchbek",
                "Bobomurodov",
                "quvonch09",
                "998905173977",
                ERole.ROLE_LIBRARIAN,
                "root123",
                new File(),
                true,
                true,
                true,
                true
        );
        User user1 = new User(
                2L,
                "Quvonchbek1",
                "Bobomurodov",
                "quvonch09",
                "998905173971",
                ERole.ROLE_LIBRARIAN,
                "root123",
                new File(),
                true,
                true,
                true,
                true
        );

        users.add(user);
        users.add(user1);
        Page<User> userPage = new PageImpl<>(users, PageRequest.of(page,size),users.size());

        when(userRepository.searchUsers(keyword,phoneNumber,ERole.ROLE_LIBRARIAN.name(),PageRequest.of(page,size))).thenReturn(userPage);

        ApiResponse apiResponse = userService.searchUsers(keyword, phoneNumber, ERole.ROLE_LIBRARIAN, page, size);
        assertNotNull(apiResponse);
        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse));
    }
}