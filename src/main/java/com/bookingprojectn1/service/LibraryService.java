package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.*;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FavouriteDTO;
import com.bookingprojectn1.payload.FeedbackDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.req.ReqLibrary;
import com.bookingprojectn1.payload.res.ResLibrary;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public ApiResponse saveLibrary(ReqLibrary reqLibrary) {
        boolean b = libraryRepository.existsByNameIgnoreCase(reqLibrary.getLibraryName());
        if (b) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Library"));
        }

        File file = fileRepository.findById(reqLibrary.getFileId()).orElse(null);

        User user = userRepository.findByUserName(reqLibrary.getOwner()).orElse(null);
        if (user == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Kechirasiz! Owner "));
        }

        Library library = Library.builder()
                .name(reqLibrary.getLibraryName())
                .lat(reqLibrary.getLat())
                .lng(reqLibrary.getLng())
                .owner(user)
                .feedbackList(null)
                .followedList(null)
                .file(file!= null ? file : null)
                .build();
        libraryRepository.save(library);
        return new ApiResponse("Successfully saved library");
    }



    public ApiResponse getAllLibraries(String name, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Library> libraries = libraryRepository.searchLibrary(name,pageRequest);

        if (libraries.getTotalElements() == 0){
            return new ApiResponse(ResponseError.NOTFOUND("Libraries"));
        }

        List<ReqLibrary> reqLibraries = new ArrayList<>();
        for (Library library : libraries) {
            ReqLibrary reqLibrary = ReqLibrary.builder()
                    .id(library.getId())
                    .libraryName(library.getName())
                    .lat(library.getLat())
                    .lng(library.getLng())
                    .fileId(library.getFile() != null ? library.getFile().getId():null)
                    .build();
            reqLibraries.add(reqLibrary);
        }
        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(libraries.getTotalPages())
                .totalElements(libraries.getTotalElements())
                .body(reqLibraries)
                .build();
        return new ApiResponse(resPageable);
    }


    public ApiResponse getOneLibrary(Long id) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        List<FeedbackDTO> feedBackLibraryDTOS = new ArrayList<>();

        List<FavouriteDTO> favouriteDTOList = new ArrayList<>();

        for (Feedback feedBackForLibrary : library.getFeedbackList()) {
            FeedbackDTO feedBackLibraryDTO = FeedbackDTO.builder()
                    .id(feedBackForLibrary.getId())
                    .message(feedBackForLibrary.getMessage())
                    .ball(feedBackForLibrary.getBall())
                    .createdBy(feedBackForLibrary.getCreatedBy().getFirstName() + " " +
                                feedBackForLibrary.getCreatedBy().getLastName())
                    .build();
            feedBackLibraryDTOS.add(feedBackLibraryDTO);
        }


        for (Favourite favourite : library.getFavouriteList()) {
            FavouriteDTO favouriteDTO = FavouriteDTO.builder()
                    .id(favourite.getId())
                    .createdBy(favourite.getCreatedBy().getFirstName() + " " + favourite.getCreatedBy().getLastName())
                    .createdDate(favourite.getCreatedAt())
                    .build();
            favouriteDTOList.add(favouriteDTO);
        }

        ResLibrary resLibrary = ResLibrary.builder()
                .libraryId(library.getId())
                .libraryName(library.getName())
                .lat(library.getLat())
                .lng(library.getLng())
                .ownerId(library.getOwner().getId())
                .favouriteCount(library.getFavouriteList().size())
                .fileId(library.getFile() != null ? library.getFile().getId():null)
                .feedBackLibraryDTOList(feedBackLibraryDTOS)
                .favourites(favouriteDTOList)
                .build();
        return new ApiResponse(resLibrary);
    }


    public ApiResponse updateLibrary(Long id,ReqLibrary reqLibrary) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        User user = userRepository.findByUserName(reqLibrary.getOwner()).orElse(null);
        if (user == null) {
            user = new User();
        }

        library.setId(id);
        library.setName(reqLibrary.getLibraryName());
        library.setLat(reqLibrary.getLat());
        library.setLng(reqLibrary.getLng());
        library.setOwner(user);
        library.setFile(fileRepository.findById(reqLibrary.getFileId()).orElse(null));
        libraryRepository.save(library);
        return new ApiResponse("Successfully updated library");
    }


    public ApiResponse deleteLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        libraryRepository.delete(library);
        return new ApiResponse("Successfully deleted library");
    }



}
