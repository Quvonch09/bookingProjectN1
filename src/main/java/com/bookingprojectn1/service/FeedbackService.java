package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.Feedback;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FeedbackDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FeedbackRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public ApiResponse saveFeedbackBook(Long bookId, FeedbackDTO feedbackDTO, User user){
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        Feedback feedback = Feedback.builder()
                .message(feedbackDTO.getMessage())
                .ball(feedbackDTO.getBall())
                .createdBy(user)
                .build();
        feedbackRepository.save(feedback);

        book.getFeedbackList().add(feedback);
        bookRepository.save(book);
        return new ApiResponse("Successfully saved Feedback");
    }


    public ApiResponse saveFeedbackLibrary(Long libraryId, FeedbackDTO feedbackDTO, User user){
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null){
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        Feedback feedback = Feedback.builder()
                .message(feedbackDTO.getMessage())
                .ball(feedbackDTO.getBall())
                .createdBy(user)
                .build();
        feedbackRepository.save(feedback);

        library.getFeedbackList().add(feedback);
        libraryRepository.save(library);
        return new ApiResponse("Successfully saved library");
    }
}
