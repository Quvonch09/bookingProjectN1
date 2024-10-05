package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.auth.AuthLogin;
import com.bookingprojectn1.payload.auth.AuthRegister;
import com.bookingprojectn1.payload.auth.ResponseLogin;
import com.bookingprojectn1.repository.UserRepository;
import com.bookingprojectn1.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    public ApiResponse login(AuthLogin authLogin)
    {
        User user = userRepository.findByPhoneNumber(authLogin.getPhoneNumber());
        if (user == null) {
            return new ApiResponse(ResponseError.NOTFOUND("User"));
        }

        if (passwordEncoder.matches(authLogin.getPassword(), user.getPassword())) {
            String token = jwtProvider.generateToken(authLogin.getPhoneNumber());
            ResponseLogin responseLogin = new ResponseLogin(token, user.getRole().name(), user.getId());
            return new ApiResponse(responseLogin);
        }

        return new ApiResponse(ResponseError.PASSWORD_DID_NOT_MATCH());
    }


    public ApiResponse register(AuthRegister auth)
    {

        User byPhoneNumber = userRepository.findByPhoneNumber(auth.getPhoneNumber());
        if (byPhoneNumber != null) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Phone number"));
        }

        saveUser(auth, ERole.ROLE_USER);

        return new ApiResponse("Success");
    }


    public ApiResponse adminSaveLibrarian(AuthRegister auth)
    {

        User byPhoneNumber = userRepository.findByPhoneNumber(auth.getPhoneNumber());
        if (byPhoneNumber != null) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Phone number"));
        }

        saveUser(auth, ERole.ROLE_LIBRARIAN);


        return new ApiResponse("Success");
    }


    private void saveUser(AuthRegister auth, ERole role)
    {
        User user = User.builder()
                .firstName(auth.getFirstName())
                .lastName(auth.getLastName())
                .phoneNumber(auth.getPhoneNumber())
                .password(passwordEncoder.encode(auth.getPassword()))
                .role(role)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        userRepository.save(user);

    }
}
