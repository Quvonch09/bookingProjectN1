package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqLibrary;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LibraryServiceTest {

    private ObjectMapper objectMapper;
    @InjectMocks
    private LibraryService libraryService;
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    void saveLibrary() {
        ReqLibrary reqLibrary = new ReqLibrary(
          1L,"Kutubxona","Quvonchbek",1234,1234,1L
        );

        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        when(libraryRepository.existsByNameIgnoreCase(reqLibrary.getName())).thenReturn(false);
        when(fileRepository.findById(reqLibrary.getFileId())).thenReturn(Optional.of(new File()));
        when(userRepository.findByUserName(reqLibrary.getUserName())).thenReturn(Optional.of(new User()));
        when(libraryRepository.save(library)).thenReturn(library);

        ApiResponse apiResponse = libraryService.saveLibrary(reqLibrary);
        assertNotNull(apiResponse);

        System.out.println("Response: "+objectMapper.writeValueAsString(apiResponse));
    }

    @Test
    @SneakyThrows
    void getAllLibraries() {
        String name = "Kutubxona";
        int page = 0;
        int size = 10;

        List<Library> libraries = new ArrayList<>();
        Library library1 = new Library(
                1L,"Kutubxona1",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        Library library2 = new Library(
                1L,"Kutubxona2",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );
        libraries.add(library1);
        libraries.add(library2);
        Page<Library> libraryPage = new PageImpl<>(libraries, PageRequest.of(page,size),libraries.size());

        when(libraryRepository.searchLibrary(name,PageRequest.of(page,size))).thenReturn(libraryPage);

        ApiResponse allLibraries = libraryService.getAllLibraries(name, page, size);
        assertNotNull(allLibraries);
        System.out.println("Response: "+objectMapper.writeValueAsString(allLibraries));
    }

    @Test
    @SneakyThrows
    void getOneLibrary() {
        Long libraryId = 1L;

        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        ApiResponse apiResponse = libraryService.getOneLibrary(libraryId);
        assertNotNull(apiResponse);

        System.out.println("Response: " + objectMapper.writeValueAsString(apiResponse));
    }

    @Test
    @SneakyThrows
    void updateLibrary() {
        Long libraryId = 1L;
        ReqLibrary reqLibrary = new ReqLibrary(
                1L,"Kutubxona","Quvonchbek",1234,1234,1L
        );

        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));
        when(libraryRepository.save(library)).thenReturn(library);

        ApiResponse apiResponse = libraryService.updateLibrary(libraryId, reqLibrary);
        assertNotNull(apiResponse);
        System.out.println("Response: " + objectMapper.writeValueAsString(apiResponse));
    }

    @Test
    @SneakyThrows
    void deleteLibrary() {
        Long libraryId = 1L;
        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        ApiResponse apiResponse = libraryService.deleteLibrary(libraryId);
        assertNotNull(apiResponse);
        System.out.println("Response: " + objectMapper.writeValueAsString(apiResponse));
    }
}