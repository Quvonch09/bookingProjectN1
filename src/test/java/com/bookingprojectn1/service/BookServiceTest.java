package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Book;
import com.bookingprojectn1.entity.Category;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.enums.BookStatus;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private FileRepository fileRepository;
    @MockBean
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
                new File(),
                new ArrayList<>(),
                new Library(),
                BookStatus.BOOKED,
                new Category()
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
                1L,
                1L,
                "BOOKED"
        );

        Long bookId = 1L;

        when(bookRepository.findById(reqBook.getBookId())).thenReturn(Optional.of(new Book()));
        when(fileRepository.findById(reqBook.getFileId())).thenReturn(Optional.of(new File()));

        ApiResponse apiResponse = bookService.updateBook(bookId, reqBook, BookStatus.BOOKED);
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
                new Library(),
                BookStatus.BOOKED,
                new Category()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        ApiResponse oneBook = bookService.getOneBook(id);
        assertNotNull(oneBook);

        System.out.println("Response: "+objectMapper.writeValueAsString(oneBook.getData()));
    }


    @Test
    @SneakyThrows
    void getAll() {
        // Test ma'lumotlari
        String title = "Nimadir";
        String description = "Nimadir";
        String author = "Quvonchbek";
        Long libraryId = 1L;
        Long categoryId = 1L;
        int page = 0;
        int size = 10;

        // Test uchun kitob ro'yxati va sahifalash (pagination) yaratish
        Book book1 = new Book(1L, "Nimadir", "Nimadir", "Quvonchbek", 300, null, null, null,BookStatus.BOOKED, null);
        Book book2 = new Book(2L, "Nimadir2", "Nimadir2", "Boshqa", 400, null, null, null,BookStatus.BOOKED,null);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        Page<Book> bookPage = new PageImpl<>(bookList, PageRequest.of(page, size), bookList.size());

        // Mock qilib metodni o'rnatish
        when(bookRepository.searchBook(title, description, author, libraryId,categoryId,BookStatus.BOOKED.name(), PageRequest.of(page, size)))
                .thenReturn(bookPage);

//        // Mock qilish - o'rtacha reyting hisoblash
//        when(bookService.calculateAverageRating(book1)).thenReturn(4.5);
//        when(bookService.calculateAverageRating(book2)).thenReturn(4.0);

        // Test method chaqirilishi
        ApiResponse apiResponse = bookService.getAllBooks(title, description, author, libraryId, categoryId,BookStatus.BOOKED,page, size);

        // Tekshirish
        assertNotNull(apiResponse);
        System.out.println(objectMapper.writeValueAsString(apiResponse));

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
                new Library(),
                BookStatus.BOOKED,
                new Category()
        );

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        ApiResponse apiResponse = bookService.deleteBook(id);
        assertNotNull(apiResponse);

        verify(bookRepository, times(1)).delete(book);

        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse.getData()));
    }
}