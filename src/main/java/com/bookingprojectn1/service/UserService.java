package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.UserDTO;
import com.bookingprojectn1.payload.auth.ResponseLogin;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.UserRepository;
import com.bookingprojectn1.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final JwtProvider jwtProvider;

    public ApiResponse getMe(User user){
        UserDTO userDTO = UserDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .fileId(user.getFile() != null ? user.getFile().getId() : null)
                .build();
        return new ApiResponse(userDTO);
    }


    public ApiResponse updateUser(Long userId,UserDTO user){
        User user1 = userRepository.findById(userId).orElse(null);
        if(user1 == null){
            return new ApiResponse(ResponseError.NOTFOUND("User"));
        }

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setFile(fileRepository.findById(user.getFileId()).orElse(null));
        userRepository.save(user1);

        String token = jwtProvider.generateToken(user1.getPhoneNumber());
        ResponseLogin responseLogin = new ResponseLogin(token, user1.getRole().name(), user1.getId());
        return new ApiResponse(responseLogin);
    }


    public ApiResponse searchUsers(String keyword,String phoneNumber, ERole role, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userRepository.searchUsers(keyword, phoneNumber, role.name(), pageRequest);
        List<UserDTO> userDTOList = new ArrayList<>();
        if(users.getTotalElements() == 0){
            return new ApiResponse(ResponseError.NOTFOUND("Users"));
        }
        for (User user : users) {
            UserDTO userDTO = UserDTO.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole().name())
                    .fileId(user.getFile() != null ? user.getFile().getId() : null)
                    .build();
            userDTOList.add(userDTO);
        }

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .body(userDTOList)
                .build();
        return new ApiResponse(resPageable);
    }
}
