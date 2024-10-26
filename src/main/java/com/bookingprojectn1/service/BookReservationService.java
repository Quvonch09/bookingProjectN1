package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.BookReservation;
import com.bookingprojectn1.entity.Followed;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.res.ResBookReservation;
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
    private final NotificationService notificationService;

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
                .startReservation(LocalDate.now())
                .endReservation(LocalDate.now().plusDays(bookReservationDTO.getDuration()))
                .build();
        bookReservationRepository.save(reservation);

        notificationService.saveNotification(
                user,
                "Hurmatli " + user.getFirstName() + " " + user.getLastName() + "!",
                "Sizga " + reservation.getEndReservation() + "kunigacha " +
                        book.getTitle() + "nomli kitobi band qilindi!",
                0L,
                false
        );

        return new ApiResponse("Book Reservation");
    }



    public ApiResponse checkNotification(User user){
        LocalDate today = LocalDate.now();   // Hozirgi vaqtni olish
        List<BookReservation> allByUser = bookReservationRepository.findAllByUser(user.getId(), today);
        if (allByUser.isEmpty()) {
            return new ApiResponse(ResponseError.NOTFOUND("Muddati tugagan kitoblar"));
        }

        // Notification yuborish
        notificationService.saveNotification(
            user,
                "Hurmatli " + user.getFirstName() + " " + user.getLastName() + "!",
                "Sizning ijaraga olgan kitoblarning muddati tugadi ",
                0L,
                false
        );

        return new ApiResponse("Notification junatildi");
    }

    public ApiResponse getReservationsByBook(Long bookId,User user) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        List<BookReservation> byBookAndReservationDateBefore = bookReservationRepository.
                findByBookAndUserAndEndReservationBefore(book.getId(), user.getId(), LocalDate.now());
        List<ResBookReservation> bookReservationDTOList = new ArrayList<>();
        if (byBookAndReservationDateBefore.isEmpty()) {
            return new ApiResponse(ResponseError.NOTFOUND("Book reservation"));
        }

        for (BookReservation bookReservation : byBookAndReservationDateBefore) {
            long epochDay = bookReservation.getStartReservation().toEpochDay();
            ResBookReservation bookReservationDTO = ResBookReservation.builder()
                    .reservationId(bookReservation.getId())
                    .bookId(bookReservation.getBook().getId())
                    .userId(bookReservation.getUser().getId())
                    .startReservationDate(bookReservation.getStartReservation())
                    .endReservationDate(bookReservation.getEndReservation())
                    .leftDays(bookReservation.getEndReservation().minusDays(epochDay).toEpochDay())
                    .build();
            bookReservationDTOList.add(bookReservationDTO);
        }

        return new ApiResponse(bookReservationDTOList);
    }


    public ApiResponse updateBookReservationDate(Long bookReservationId, Long reservationDuration) {
        BookReservation bookReservation = bookReservationRepository.findById(bookReservationId).orElse(null);
        if (bookReservation == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book reservation"));
        }
        bookReservation.setEndReservation(bookReservation.getEndReservation().plusDays(reservationDuration));
        bookReservationRepository.save(bookReservation);
        return new ApiResponse("Book Reservation date successfully updated");
    }


    public ApiResponse deleteReservation(Long reservationId){
        BookReservation bookReservation = bookReservationRepository.findById(reservationId).orElse(null);
        if (bookReservation == null){
            return new ApiResponse(ResponseError.NOTFOUND("BookReservation"));
        }

        bookReservationRepository.delete(bookReservation);
        return new ApiResponse("Successfully deleted bookReservation");
    }
}
