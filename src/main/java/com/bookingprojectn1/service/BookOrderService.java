package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.BookOrder;
import com.bookingprojectn1.entity.Followed;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.BookStatus;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.BookReservationDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.res.ResBookReservation;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.BookReservationRepository;
import com.bookingprojectn1.repository.FollowedRepository;
import com.bookingprojectn1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookOrderService {
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

        BookOrder reservation = BookOrder.builder()
                .book(book)
                .user(user)
                .startReservation(bookReservationDTO.getStartDate())
                .endReservation(bookReservationDTO.getStartDate().plusDays(bookReservationDTO.getDuration()))
                .build();
        bookReservationRepository.save(reservation);

        book.setStatus(BookStatus.BOOKED);
        bookRepository.save(book);

        notificationService.saveNotification(
                user,
                "Hurmatli " + user.getFirstName() + " " + user.getLastName() + "!",
                "Siz " + reservation.getEndReservation() + " kunigacha " +
                        book.getTitle() + " nomli kitobi band qilindi!",
                false
        );

        return new ApiResponse("Book Reservation saved");
    }



    public ApiResponse checkNotification(User user){
        LocalDate today = LocalDate.now();   // Hozirgi vaqtni olish
        List<BookOrder> allByUser = bookReservationRepository.findAllByUser(user.getId(), today);
        if (allByUser.isEmpty()) {
            return new ApiResponse(ResponseError.NOTFOUND("Muddati tugagan kitoblar"));
        }

        for (BookOrder bookOrder : allByUser) {
            Book book = bookOrder.getBook();
            book.setStatus(BookStatus.BORROWED);
            bookRepository.save(book); 
        }

        // Notification yuborish
        notificationService.saveNotification(
            user,
                "Hurmatli " + user.getFirstName() + " " + user.getLastName() + "!",
                "Sizning ijaraga olgan kitoblarning muddati tugadi ",
                false
        );

        return new ApiResponse("Notification junatildi");
    }

    public ApiResponse getReservationsByBook(Long bookId,Long userId) {

        List<BookOrder> byBookAndReservationDateBefore = bookReservationRepository.
                findByBookAndUserAndEndReservationBefore(bookId, userId, LocalDate.now());
        List<ResBookReservation> bookReservationDTOList = new ArrayList<>();
        if (byBookAndReservationDateBefore.isEmpty()) {
            return new ApiResponse(ResponseError.NOTFOUND("Book reservation"));
        }

        for (BookOrder bookOrder : byBookAndReservationDateBefore) {
            long epochDay = bookOrder.getStartReservation().toEpochDay();
            ResBookReservation bookReservationDTO = ResBookReservation.builder()
                    .reservationId(bookOrder.getId())
                    .bookId(bookOrder.getBook().getId())
                    .userId(bookOrder.getUser().getId())
                    .orderStartDate(bookOrder.getStartReservation())
                    .orderEndDate(bookOrder.getEndReservation())
                    .leftDays(bookOrder.getEndReservation().minusDays(epochDay).toEpochDay())
                    .build();
            bookReservationDTOList.add(bookReservationDTO);
        }

        return new ApiResponse(bookReservationDTOList);
    }

    public ApiResponse search(Long user, Long bookId, LocalDate startDate, LocalDate endDate, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookOrder> all = bookReservationRepository.findAll(bookId, user, startDate, endDate, pageRequest);
        List<ResBookReservation> bookReservationDTOList = new ArrayList<>();
        for (BookOrder bookOrder : all) {
            long epochDay = bookOrder.getStartReservation().toEpochDay();
            ResBookReservation resBookReservation = ResBookReservation.builder()
                    .reservationId(bookOrder.getId())
                    .bookId(bookOrder.getBook().getId())
                    .userId(bookOrder.getUser().getId())
                    .orderStartDate(bookOrder.getStartReservation())
                    .orderEndDate(bookOrder.getEndReservation())
                    .leftDays(bookOrder.getEndReservation().minusDays(epochDay).toEpochDay())
                    .build();
            bookReservationDTOList.add(resBookReservation);
        }

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(bookReservationDTOList)
                .build();
        return new ApiResponse(resPageable);
    }


    public ApiResponse updateBookReservationDate(Long bookReservationId, Long reservationDuration) {
        BookOrder bookOrder = bookReservationRepository.findById(bookReservationId).orElse(null);
        if (bookOrder == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book reservation"));
        }
        bookOrder.setEndReservation(bookOrder.getEndReservation().plusDays(reservationDuration));
        bookReservationRepository.save(bookOrder);
        return new ApiResponse("Book Reservation date successfully updated");
    }


    public ApiResponse deleteReservation(Long reservationId){
        BookOrder bookOrder = bookReservationRepository.findById(reservationId).orElse(null);
        if (bookOrder == null){
            return new ApiResponse(ResponseError.NOTFOUND("BookOrder"));
        }

        bookReservationRepository.delete(bookOrder);
        return new ApiResponse("Successfully deleted bookOrder");
    }
}
