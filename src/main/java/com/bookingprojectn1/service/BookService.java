package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.FeedBackForBook;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FeedBackBookDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.payload.res.ResBook;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final FileRepository fileRepository;

    public ApiResponse addBook(ReqBook reqBook) {
        File file = fileRepository.findById(reqBook.getFileId()).orElse(null);
        if (file == null) {
            return new ApiResponse(ResponseError.NOTFOUND("File"));
        }

        Book book = Book.builder()
                .title(reqBook.getTitle())
                .author(reqBook.getAuthor())
                .pageCount(reqBook.getPageCount())
                .file(file)
                .feedBackForBook(null)
                .build();
        bookRepository.save(book);

        return new ApiResponse("Successfully added Book");
    }


    public ApiResponse getAllBooks(String title,String author,int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Book> books = bookRepository.searchBook(title,author,pageRequest);
        List<ReqBook> reqBookList = new ArrayList<>();
        for (Book book : books) {
            reqBookList.add(reqBook(book));
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

        List<FeedBackBookDTO> feedBackBookDTOList = new ArrayList<>();
        for (FeedBackForBook feedBackForBook : book.getFeedBackForBook()) {
            FeedBackBookDTO feedBackBookDTO = FeedBackBookDTO.builder()
                    .message(feedBackForBook.getMessage())
                    .ball(feedBackForBook.getBall())
                    .createdBy(feedBackForBook.getCreatedBy().getFirstName() + " " + feedBackForBook.getCreatedBy().getLastName())
                    .bookId(feedBackForBook.getBook().getId())
                    .build();
            feedBackBookDTOList.add(feedBackBookDTO);
        }

        ResBook resBook = ResBook.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .pageCount(book.getPageCount())
                .fileId(book.getFile().getId())
                .feedBackBook(feedBackBookDTOList)
                .build();

        return new ApiResponse(resBook);
    }


    public ApiResponse updateBook(Long bookId,ReqBook reqBook) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }

        book.setTitle(reqBook.getTitle());
        book.setAuthor(reqBook.getAuthor());
        book.setPageCount(reqBook.getPageCount());
        book.setFile(fileRepository.findById(reqBook.getFileId()).orElse(null));
        bookRepository.save(book);
        return new ApiResponse("Successfully updated Book");
    }


    public ApiResponse deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Book"));
        }
        bookRepository.delete(book);
        return new ApiResponse("Successfully deleted Book");
    }


    private ReqBook reqBook(Book book) {
        return ReqBook.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .pageCount(book.getPageCount())
                .fileId(book.getFile().getId())
                .build();
    }

}
