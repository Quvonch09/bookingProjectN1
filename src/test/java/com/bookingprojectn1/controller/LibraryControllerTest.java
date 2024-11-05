package com.bookingprojectn1.controller;

import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.entity.enums.ERole;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.req.ReqLibrary;
import com.bookingprojectn1.payload.res.ResLibrary;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
import com.bookingprojectn1.service.LibraryService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class LibraryControllerTest {
    private ObjectMapper objectMapper;
    @Autowired
    private LibraryController libraryController;
    @MockBean
    private LibraryService libraryService;
    @MockBean
    private LibraryRepository libraryRepository;
    @MockBean
    private FileRepository fileRepository;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
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
        when(libraryService.saveLibrary(reqLibrary)).thenReturn(new ApiResponse("Successfully saved library"));

        ResponseEntity<ApiResponse> response = libraryController.saveLibrary(reqLibrary);

        System.out.println("Response: " + objectMapper.writeValueAsString(response));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void searchLibrary() {
        String title = "Kutubxona";
        int page = 0;
        int size = 10;
        User user = new User(
                2L,
                "Quvonchbek",
                "Bobomurodov",
                "quvonch09",
                "998905173977",
                ERole.ROLE_ADMIN,
                "root123",
                new File(),
                true,
                true,
                true,
                true
        );
        Library library = new Library(
                1L,"Kutubxona",1234,1234,user,new ArrayList<>(),new ArrayList<>(),new File()
        );
        Library library1 = new Library(
                1L,"Kutubxona1",1234,1234,user,new ArrayList<>(),new ArrayList<>(),new File()
        );
        List<Library> libraries = new ArrayList<>();
        libraries.add(library);
        libraries.add(library1);
        Page<Library> librariesPage = new PageImpl<>(libraries, PageRequest.of(page, size),libraries.size());
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(librariesPage.getTotalPages())
                .totalElements(librariesPage.getTotalElements())
                .body(librariesPage)
                .build();
        ApiResponse apiResponse = new ApiResponse(resPageable);

        when(libraryRepository.searchLibrary(title,PageRequest.of(page,size))).thenReturn(librariesPage);
        when(libraryService.getAllLibraries(title,page,size)).thenReturn(apiResponse);

        ResponseEntity<ApiResponse> response = libraryController.searchLibrary(title,page,size);
        System.out.println("Response: " + objectMapper.writeValueAsString(response));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void getOneLibrary() {
        Long id = 1L;
        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );
        ResLibrary resLibrary = new ResLibrary(1L,"Kutubxona",1L,123,2345,1L,new ArrayList<>());

        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
        when(libraryService.getOneLibrary(id)).thenReturn(new ApiResponse(resLibrary));
        ResponseEntity<ApiResponse> response = libraryController.getOneLibrary(id);
        System.out.println("Response: " + objectMapper.writeValueAsString(response));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void updateLibrary() {
        Long libraryId = 1L;
        ReqLibrary reqLibrary = new ReqLibrary(
                1L,"Kutubxona","Quvonchbek",1234,1234,1L
        );

        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));
        when(userRepository.findByUserName(reqLibrary.getUserName())).thenReturn(Optional.of(new User()));
        when(libraryRepository.save(library)).thenReturn(library);
        when(libraryService.updateLibrary(libraryId, reqLibrary)).thenReturn(new ApiResponse("Successfully updated library"));
        ResponseEntity<ApiResponse> response = libraryController.updateLibrary(libraryId, reqLibrary);
        System.out.println("Response: " + objectMapper.writeValueAsString(response));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    void deleteLibrary() {
        Long id = 1L;

        Library library = new Library(
                1L,"Kutubxona",1234,1234,new User(),new ArrayList<>(),new ArrayList<>(),new File()
        );
         when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
         doNothing().when(libraryRepository).delete(library);
         when(libraryService.deleteLibrary(id)).thenReturn(new ApiResponse("Successfully deleted library"));
         ResponseEntity<ApiResponse> response = libraryController.deleteLibrary(id);
         System.out.println("Response: " + objectMapper.writeValueAsString(response));
    }
}