package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.Category;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.enums.BookStatus;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest
public class BookControllerTest {

    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private LibraryRepository libraryRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
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
                "2024",
                1L,
                1L,
                1L,
                1L,
                "BOOKED"
        );

        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                "2024",
                new File(),
                new File(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Library(),
                BookStatus.BOOKED,
                new Category()
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
                "2024",
                1L,
                1L,
                1L,
                1L,
                "BOOKED"
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
                "2024",
                new File(),
                new File(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Library(),
                BookStatus.BOOKED,
                new Category()
        );

        ResBook resBook = new ResBook(
                1L,
                "Nimadir",
                "nimadir",
                4L,
                "Quvonchbek",
                "2024",
                10,
                1L,
                1L,
                1L,
                1L,
                "BOOKED",
                10,
                new ArrayList<>(),
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
    @WithMockUser(roles = {"ADMIN"})
    void getAll() {
        String title = "Nimadir";
        String description = "Nimadir";
        String author = "Quvonchbek";
        String year = "2024";
        Long libraryId = 1L;
        Long categoryId = 1L;
        int page = 0;
        int size = 10;
        Book book1 = new Book(1L, "Nimadir1", "Nimadir1", "Quvonchbek1", 300,"2024", null, null,null,null, null,BookStatus.BOOKED, null);
        Book book2 = new Book(2L, "Nimadir2", "Nimadir2", "Quvonchbek2", 300,"2024", null, null,null,null, null,BookStatus.BOOKED, null);
        Book book3 = new Book(3L, "Nimadir3", "Nimadir3", "Quvonchbek3", 300,"2024", null, null,null,null, null,BookStatus.BOOKED, null);
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

        when(bookRepository.searchBook(title,description,author,year,libraryId,categoryId,BookStatus.BOOKED.name(), PageRequest.of(page,size))).thenReturn(bookPage);
        when(bookService.getAllBooks(title,description,author,year,libraryId,categoryId,BookStatus.BOOKED,page,size)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = bookController.searchBook(title, description, author, year, libraryId,categoryId,BookStatus.BOOKED, page, size);
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
                "2024",
                1L,
                1L,
                1L,
                1L,
                "BOOKED"
        );

        Book book = new Book(
                1L,
                "Nimadir",
                "nimadir",
                "Quvonchbek",
                10,
                "2024",
                new File(),
                new File(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Library(),
                BookStatus.BOOKED,
                new Category()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookService.updateBook(id,reqBook,BookStatus.BOOKED)).thenReturn(new ApiResponse("Successfully updated Book"));

        ResponseEntity<ApiResponse> response = bookController.updateBook(id, reqBook,BookStatus.BOOKED);
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
                "2024",
                new File(),
                new File(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Library(),
                BookStatus.BOOKED,
                new Category()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);
        when(bookService.deleteBook(id)).thenReturn(new ApiResponse("Successfully deleted Book"));

        ResponseEntity<ApiResponse> response = bookController.deleteBook(id);
        System.out.println(objectMapper.writeValueAsString(response));
    }
}

