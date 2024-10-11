package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.BookReservation;
import com.bookingprojectn1.entity.Followed;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.BookReservationRepository;
import com.bookingprojectn1.repository.FollowedRepository;
import com.bookingprojectn1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReservationService {
    private final BookReservationRepository bookReservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FollowedRepository followedRepository;

    public ApiResponse reverseBook(BookReservationDTO bookReservationDTO) {
        Book book = bookRepository.findById(bookReservationDTO.getBookId()).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        User user = userRepository.findById(bookReservationDTO.getUserId()).orElse(null);
        if (user == null) {
            return new ApiResponse(ResponseError.NOTFOUND("User"));
        }

        List<Followed> allByUser = followedRepository.findAllFollowed(user.getId(),book.getLibrary().getId());
        for (Followed followed : allByUser) {
            if (!followed.getUser().equals(user)) {
                return new ApiResponse(
                        ResponseError.DEFAULT_ERROR("Siz bu kutubxonadan kitob ololmaysiz chunki siz obuna bo'lmagansiz"));
            }
        }


        BookReservation reservation = BookReservation.builder()
                .book(book)
                .user(user)
                .reservationDate(bookReservationDTO.getReservationDate())
                .build();
        bookReservationRepository.save(reservation);

        return new ApiResponse("Book Reservation");
    }

    public ApiResponse getReservationsByBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        List<BookReservation> byBookAndReservationDateBefore = bookReservationRepository.
                findByBookAndReservationDateBefore(book, LocalDate.now());
        List<BookReservationDTO> bookReservationDTOList = new ArrayList<>();
        if (byBookAndReservationDateBefore.isEmpty()) {
            return new ApiResponse(ResponseError.NOTFOUND("Book reservation"));
        }

        for (BookReservation bookReservation : byBookAndReservationDateBefore) {
            BookReservationDTO bookReservationDTO = BookReservationDTO.builder()
                    .reservationId(bookReservation.getId())
                    .bookId(bookReservation.getBook().getId())
                    .userId(bookReservation.getUser().getId())
                    .reservationDate(bookReservation.getReservationDate())
                    .build();
            bookReservationDTOList.add(bookReservationDTO);
        }

        return new ApiResponse(bookReservationDTOList);
    }
}
