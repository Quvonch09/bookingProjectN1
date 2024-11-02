package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private LibraryRepository libraryRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
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


        when(fileRepository.findById(reqBook.getFileId())).thenReturn(Optional.of(new File()));
        when(libraryRepository.findById(reqBook.getLibraryId())).thenReturn(Optional.of(new Library()));
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse apiResponse = bookService.addBook(reqBook);

        assertNotNull(apiResponse);

        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
    }

    @SneakyThrows
    @Test
    void updateBookTest() {
        ReqBook reqBook = new ReqBook(
                1L,
                "Book",
                4L,
                "book",
                "Quvonchbek",
                10,
                1L,
                1L
        );

        Long bookId = 1L;

        when(bookRepository.findById(reqBook.getBookId())).thenReturn(Optional.of(new Book()));
        when(fileRepository.findById(reqBook.getFileId())).thenReturn(Optional.of(new File()));

        ApiResponse apiResponse = bookService.updateBook(bookId, reqBook);
        assertNotNull(apiResponse);

        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
    }


    @SneakyThrows
    @Test
    void getById() {
        Long id = 1L;

        Book book = new Book(
                1L,
                "Book",
                "Nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse oneBook = bookService.getOneBook(id);
        assertNotNull(oneBook);

        System.out.println("Response: "+objectMapper.writeValueAsString(oneBook.getData()));
    }

    @SneakyThrows
    @Test
    void deleteBook() {
        Long id = 1L;

        Book book = new Book(
                1L,
                "Book",
                "Nimadir",
                "Quvonchbek",
                10,
                new File(),
                new ArrayList<>(),
                new Library()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        ApiResponse apiResponse = bookService.deleteBook(id);
        assertNotNull(apiResponse);

        verify(bookRepository, times(1)).delete(book);

        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
    }
}