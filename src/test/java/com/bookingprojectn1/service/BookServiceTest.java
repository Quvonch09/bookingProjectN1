package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqBook;
import com.bookingprojectn1.repository.BookRepository;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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

        try {
            System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}