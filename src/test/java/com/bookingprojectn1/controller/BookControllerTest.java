package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.payload.res.ResBook;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    private ObjectMapper objectMapper;

    @Mock
    private BookService bookService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }


    @SneakyThrows
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void saveBook() {
        ReqBook reqBook = new ReqBook(
                1L,
                "Nimadir",
                4L,
                "nimadir",
                "Quvonchbek",
                10,
                1L,
                1L
        );

        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        ApiResponse apiResponse = new ApiResponse("Successfully saved Book");

        when(fileRepository.findById(reqBook.getFileId())).thenReturn(Optional.of(new File()));
        when(libraryRepository.findById(reqBook.getLibraryId())).thenReturn(Optional.of(new Library()));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookService.addBook(Mockito.any(ReqBook.class))).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = bookController.addBook(reqBook);

        System.out.println(objectMapper.writeValueAsString(response));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void addBook() {
        ReqBook reqBook = new ReqBook(
                1L,
                "Nimadir",
                4L,
                "nimadir",
                "Quvonchbek",
                10,
                1L,
                1L
        );

        when(bookService.addBook(reqBook)).thenReturn(new ApiResponse("Successfully saved Book"));

        ResponseEntity<ApiResponse> response = bookController.addBook(reqBook);

        System.out.println(objectMapper.writeValueAsString(response));

    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void getById() {
        Long id = 1L;

        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        ResBook resBook = new ResBook(
                1L,
                "nimadir",
                "nimadir",
                4L,
                "quvonchbek",
                10,
                1L,
                1L,
                new ArrayList<>()
        );

        ApiResponse apiResponse = new ApiResponse(resBook);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookService.getOneBook(id)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = bookController.getOneBook(id);
        System.out.println(objectMapper.writeValueAsString(response));
    }


    @Test
    @SneakyThrows
    void getAll() {
        String title = "Nimadir";
        String description = "Nimadir";
        String author = "Quvonchbek";
        Long libraryId = 1L;
        int page = 0;
        int size = 10;
        Book book1 = new Book(1L, "Nimadir", "Nimadir", "Quvonchbek", 300, null, null, null);
        Book book2 = new Book(2L, "Nimadir2", "Nimadir2", "Boshqa", 400, null, null, null);
        Book book3 = new Book(3L, "Nimadir2", "Nimadir3", "Boshqa", 400, null, null, null);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        Page<Book> bookPage = new PageImpl<>(bookList, PageRequest.of(page, size), bookList.size());
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(bookPage.getTotalPages())
                .totalElements(bookPage.getTotalElements())
                .body(bookPage)
                .build();
        ApiResponse apiResponse = new ApiResponse(resPageable);

        when(bookRepository.searchBook(title,description,author,libraryId,PageRequest.of(page,size))).thenReturn(bookPage);
        when(bookService.getAllBooks(title,description,author,libraryId,page,size)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = bookController.searchBook(title, description, author, libraryId, page, size);
        System.out.println(objectMapper.writeValueAsString(response));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void updateBook() {
        Long id = 1L;
        ReqBook reqBook = new ReqBook(
                1L,
                "Nimadir",
                4L,
                "nimadir",
                "Quvonchbek",
                10,
                1L,
                1L
        );

        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookService.updateBook(id,reqBook)).thenReturn(new ApiResponse("Successfully updated Book"));

        ResponseEntity<ApiResponse> response = bookController.updateBook(id, reqBook);
        System.out.println(objectMapper.writeValueAsString(response));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void deleteBook() {
        Long id = 1L;
        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);
        when(bookService.deleteBook(id)).thenReturn(new ApiResponse("Successfully deleted Book"));

        ResponseEntity<ApiResponse> response = bookController.deleteBook(id);
        System.out.println(objectMapper.writeValueAsString(response));
    }
}

