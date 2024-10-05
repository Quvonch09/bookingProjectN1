package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.UserBooks;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.req.ReqUserBooks;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.payload.res.ResUserBooks;
import com.bookingprojectn1.repository.UserBooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBooksService {
    private final UserBooksRepository userBooksRepository;


    public ApiResponse saveUserBooks(ReqUserBooks reqUserBooks) {
        boolean b = userBooksRepository.existsByBookIdAndUsername(reqUserBooks.getBookId(), reqUserBooks.getUserName());
        if (b) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("User Books already exist"));
        }
        LocalDateTime now = LocalDateTime.now();

        UserBooks userBooks = UserBooks.builder()
                .bookId(reqUserBooks.getBookId())
                .userName(reqUserBooks.getUserName())
                .startTime(now)
                .duration(Duration.ofDays(reqUserBooks.getDuration()))
                .endTime(now.plusDays(reqUserBooks.getDuration()))
                .submitted(false)
                .build();
        userBooksRepository.save(userBooks);
        return new ApiResponse("User Books saved successfully");
    }

    public ApiResponse searchUserBooks(Long bookId, String userName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserBooks> userBooks = userBooksRepository.searchUserBooks(bookId, userName, pageRequest);

        if (userBooks.getTotalElements() == 0) {
            return new ApiResponse(ResponseError.NOTFOUND("User Books"));
        }

        List<ReqUserBooks> reqUserBooksList = new ArrayList<>();

        for (UserBooks userBook : userBooks) {
            ReqUserBooks reqUserBooks = ReqUserBooks.builder()
                    .id(userBook.getId())
                    .bookId(userBook.getBookId())
                    .userName(userBook.getUserName())
                    .duration(userBook.getDuration().toDays())
                    .build();
            reqUserBooksList.add(reqUserBooks);
        }
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(userBooks.getTotalPages())
                .totalElements(userBooks.getTotalElements())
                .body(reqUserBooksList)
                .build();
        return new ApiResponse(resPageable);
    }


    public ApiResponse getOneUserBooks(Long userBooksId) {
        UserBooks userBooks = userBooksRepository.findById(userBooksId).orElse(null);
        if (userBooks == null) {
            return new ApiResponse(ResponseError.NOTFOUND("UserBooks"));
        }
        ResUserBooks resUserBooks = ResUserBooks.builder()
                .id(userBooks.getId())
                .bookId(userBooks.getBookId())
                .userName(userBooks.getUserName())
                .startTime(userBooks.getStartTime())
                .endTime(userBooks.getEndTime())
                .submitted(userBooks.isSubmitted())
                .duration(userBooks.getDuration().toDays())
                .build();
        return new ApiResponse(resUserBooks);
    }


    public ApiResponse updateUserBooks(Long userBooksId, ReqUserBooks reqUserBooks) {
        UserBooks userBooks = userBooksRepository.findById(userBooksId).orElse(null);
        if (userBooks == null) {
            return new ApiResponse(ResponseError.NOTFOUND("UserBooks"));
        }

        userBooks.setId(userBooksId);
        userBooks.setUserName(reqUserBooks.getUserName());
        userBooks.setBookId(reqUserBooks.getBookId());
        userBooks.setDuration(Duration.ofDays(reqUserBooks.getDuration()));
        userBooks.setEndTime(LocalDateTime.now().plusDays(reqUserBooks.getDuration()));
        userBooksRepository.save(userBooks);
        return new ApiResponse("User Books updated successfully");
    }


    public ApiResponse deleteUserBooks(Long userBooksId) {
        UserBooks userBooks = userBooksRepository.findById(userBooksId).orElse(null);
        if (userBooks == null) {
            return new ApiResponse(ResponseError.NOTFOUND("UserBooks"));
        }
        userBooksRepository.delete(userBooks);
        return new ApiResponse("User Books deleted successfully");
    }
}
