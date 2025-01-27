package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.Favourite;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FavouriteRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public ApiResponse saveBookFavourite(Long bookId, User currentUser) {

        Optional<Favourite> favourite1 = favouriteRepository.findFavouriteBook(bookId, currentUser.getId());
        if (favourite1.isPresent()) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Kechirasiz bu kitob uchun bu user favoutire "));
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        Favourite favourite = Favourite.builder()
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .build();
        favouriteRepository.save(favourite);

        book.getFavouriteList().add(favourite);
        bookRepository.save(book);

        return new ApiResponse("Successfully saved a favourite");
    }


    public ApiResponse saveLibraryFavourite(Long libraryId, User currentUser) {

        Optional<Favourite> favourite1 = favouriteRepository.findFavouriteLibrary(libraryId, currentUser.getId());
        if (favourite1.isPresent()) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Kechirasiz bu kutubxona uchun bu user favoutire "));
        }

        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        Favourite favourite = Favourite.builder()
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .build();
        favouriteRepository.save(favourite);
        library.getFavouriteList().add(favourite);
        libraryRepository.save(library);

        return new ApiResponse("Successfully saved a favourite");
    }


    @Transactional
    public ApiResponse deleteBookFavourite(Long bookId, User currentUser) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        for (Favourite favourite : book.getFavouriteList()) {
            if (favourite.getCreatedBy().equals(currentUser)) {
                favouriteRepository.deleteFavouriteBook(favourite.getId());
                favouriteRepository.delete(favourite);
                book.getFavouriteList().remove(favourite);
            }
        }
        bookRepository.save(book);

        return new ApiResponse("Successfully deleted a favourite");
    }


    public ApiResponse deleteLibraryFavourite(Long libraryId, User currentUser) {
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        for (Favourite favourite : library.getFavouriteList()) {
            if (favourite.getCreatedBy().equals(currentUser)) {
                favouriteRepository.deleteFavouriteLibrary(favourite.getId());
                favouriteRepository.delete(favourite);
                library.getFavouriteList().remove(favourite);
            }
        }
        libraryRepository.save(library);
        return new ApiResponse("Successfully deleted a favourite");
    }
}
