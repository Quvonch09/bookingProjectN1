package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.*;
import com.bookingprojectn1.entity.enums.BookStatus;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FavouriteDTO;
import com.bookingprojectn1.payload.FeedbackDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.payload.res.ResBook;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final FileRepository fileRepository;
    private final LibraryRepository libraryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ApiResponse addBook(ReqBook reqBook) {
//        bu joyida kitob nomini uniquelikka tekshirishni uylab chiqish kk
        boolean b = bookRepository.existsByTitleIgnoreCase(reqBook.getTitle());
        if (b){
            return new ApiResponse(ResponseError.ALREADY_EXIST("Bu nomli kitob"));
        }

        File pdf = fileRepository.findById(reqBook.getFileId()).orElse(null);
        File bookImg = fileRepository.findById(reqBook.getBookImgId()).orElse(null);

        Library library = libraryRepository.findById(reqBook.getLibraryId()).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        SubCategory subCategory = subCategoryRepository.findById(reqBook.getSubCategoryId()).orElse(null);
        if (subCategory == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category"));
        }

        Book book = Book.builder()
                .title(reqBook.getTitle())
                .description(reqBook.getDescription())
                .author(reqBook.getAuthor())
                .pageCount(reqBook.getPageCount())
                .pdf(pdf!=null ? pdf : null)
                .bookImg(bookImg!=null ? bookImg : null)
                .feedbackList(null)
                .year(reqBook.getYear())
                .library(library)
                .category(subCategory.getCategory())
                .subCategory(subCategory)
                .status(BookStatus.BORROWED)
                .build();
        bookRepository.save(book);

        return new ApiResponse("Successfully added Book");
    }


    public ApiResponse getAllBooks(String title,String description,String author,String year,Long libraryId,
                                   Long categoryId,Long subCategoryId,BookStatus bookStatus,int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> books = bookRepository.searchBook(title,description,author,year,libraryId,categoryId,subCategoryId,bookStatus.name(),pageRequest);
        List<ReqBook> reqBookList = new ArrayList<>();
        for (Book book : books) {
            double v = calculateAverageRating(book);
            reqBookList.add(reqBook(book,v));
        }
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(books.getTotalPages())
                .totalElements(books.getTotalElements())
                .body(reqBookList)
                .build();
        return new ApiResponse(resPageable);
    }


    public ApiResponse getOneBook(Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        List<FeedbackDTO> feedBackBookDTOList = new ArrayList<>();
        List<FavouriteDTO> favouriteDTOList = new ArrayList<>();
        for (Feedback feedBackForBook : book.getFeedbackList()) {
            FeedbackDTO feedBackBookDTO = FeedbackDTO.builder()
                    .id(feedBackForBook.getId())
                    .message(feedBackForBook.getMessage())
                    .ball(feedBackForBook.getBall())
                    .createdBy(feedBackForBook.getCreatedBy().getFirstName() + " " + feedBackForBook.getCreatedBy().getLastName())
                    .build();
            feedBackBookDTOList.add(feedBackBookDTO);
        }

        for (Favourite favourite : book.getFavouriteList()) {
            FavouriteDTO favouriteDTO = FavouriteDTO.builder()
                    .id(favourite.getId())
                    .createdBy(favourite.getCreatedBy().getFirstName() + " " + favourite.getCreatedBy().getLastName())
                    .createdDate(favourite.getCreatedAt())
                    .build();
            favouriteDTOList.add(favouriteDTO);
        }

        ResBook resBook = ResBook.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .rate(Math.round(calculateAverageRating(book)))
                .author(book.getAuthor())
                .pageCount(book.getPageCount())
                .year(book.getYear())
                .pdfId(book.getPdf() != null ? book.getPdf().getId() : null)
                .bookImgId(book.getBookImg() != null ? book.getBookImg().getId() : null)
                .libraryId(book.getLibrary().getId())
                .categoryId(book.getCategory().getId())
                .subCategoryId(book.getSubCategory().getId())
                .bookStatus(book.getStatus().name())
                .favouriteCount(book.getFavouriteList().size())
                .feedBackBook(feedBackBookDTOList)
                .favouriteDTOList(favouriteDTOList)
                .build();

        return new ApiResponse(resBook);
    }


    public ApiResponse updateBook(Long bookId,ReqBook reqBook, BookStatus status) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        Library library = libraryRepository.findById(reqBook.getLibraryId()).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        SubCategory subCategory = subCategoryRepository.findById(reqBook.getSubCategoryId()).orElse(null);
        if (subCategory == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Category"));
        }

        book.setTitle(reqBook.getTitle());
        book.setDescription(reqBook.getDescription());
        book.setAuthor(reqBook.getAuthor());
        book.setPageCount(reqBook.getPageCount());
        book.setPdf(fileRepository.findById(reqBook.getFileId()).orElse(null));
        book.setBookImg(fileRepository.findById(reqBook.getBookImgId()).orElse(null));
        book.setLibrary(library);
        book.setYear(reqBook.getYear());
        book.setCategory(subCategory.getCategory());
        book.setSubCategory(subCategory);
        book.setStatus(status);
        bookRepository.save(book);
        return new ApiResponse("Successfully updated Book");
    }



    public ApiResponse updateStatusBook(Long bookId, BookStatus status)
    {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null){
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }
        book.setStatus(status);
        bookRepository.save(book);
        return new ApiResponse("Successfully updated book status");
    }


    public ApiResponse deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }
        bookRepository.delete(book);
        return new ApiResponse("Successfully deleted Book");
    }




    public double calculateAverageRating(Book book) {
        List<Feedback> feedbackList = book.getFeedbackList();
        if (feedbackList == null || feedbackList.isEmpty()) {
            return 0; // Feedback yo'q bo'lsa, 0 reyting qaytariladi
        }
        double total = 0;
        for (Feedback feedback : feedbackList) {
            total += feedback.getBall(); // Har bir feedbackdagi ballni yig'amiz
        }
        return total / feedbackList.size(); // O'rtacha qiymat qaytariladi
    }


    public ApiResponse rateBook(){
        List<ReqBook> reqBookList = new ArrayList<>();
        List<Book> bookList = bookRepository.findAll();
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book b1, Book b2) {
                    double avgRating1 = calculateAverageRating(b1);
                    double avgRating2 = calculateAverageRating(b2);
                    return Double.compare(avgRating2, avgRating1); // Kamayish tartibida
                }
            });
        for (Book book : bookList) {
            double rating = calculateAverageRating(book);
            ReqBook reqBook = reqBook(book,rating);
            reqBookList.add(reqBook);
        }
        return new ApiResponse(reqBookList);

    }



    private ReqBook reqBook(Book book,Double rate) {
        return ReqBook.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .rate(Math.round(rate))
                .author(book.getAuthor())
                .year(book.getYear())
                .pageCount(book.getPageCount())
                .fileId(book.getPdf()!=null ? book.getPdf().getId():null)
                .bookImgId(book.getBookImg()!=null ? book.getBookImg().getId():null)
                .libraryId(book.getLibrary()!= null ? book.getLibrary().getId():null)
                .subCategoryId(book.getCategory() != null ? book.getCategory().getId():null)
                .bookStatus(book.getStatus().name())
                .build();
    }

}
