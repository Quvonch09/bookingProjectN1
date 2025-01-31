package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.res.ResAdmin;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    public ApiResponse countAll(){
        Long adminCount = userRepository.countByRoleAndEnabledTrue(ERole.ROLE_ADMIN);
        Long count = libraryRepository.count();
        Long userCount = userRepository.countByRoleAndEnabledTrue(ERole.ROLE_USER);
        ResAdmin resAdmin = ResAdmin.builder()
                .countAdmins(adminCount)
                .countLibraries(count)
                .countUsers(userCount)
                .build();
        return new ApiResponse(resAdmin);
    }
}
