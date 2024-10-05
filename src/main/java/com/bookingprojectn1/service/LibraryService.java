package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Feedback;
import com.bookingprojectn1.entity.File;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FeedBackLibraryDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.payload.req.ReqLibrary;
import com.bookingprojectn1.payload.res.ResLibrary;
import com.bookingprojectn1.payload.res.ResPageable;
import com.bookingprojectn1.repository.FileRepository;
import com.bookingprojectn1.repository.LibraryRepository;
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

    public ApiResponse saveLibrary(ReqLibrary reqLibrary) {
        boolean b = libraryRepository.existsByNameIgnoreCase(reqLibrary.getName());
        if (b) {
            return new ApiResponse(ResponseError.ALREADY_EXIST("Library already exists"));
        }

        File file = fileRepository.findById(reqLibrary.getFileId()).orElse(null);
        if (file == null) {
            file = new File();
        }

        Library library = Library.builder()
                .name(reqLibrary.getName())
                .lat(reqLibrary.getLat())
                .lng(reqLibrary.getLng())
                .feedbackList(null)
                .file(file)
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
                    .name(library.getName())
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

        List<FeedBackLibraryDTO> feedBackLibraryDTOS = new ArrayList<>();

        if (library.getFeedbackList().isEmpty()){
            feedBackLibraryDTOS=null;
        }

        for (Feedback feedBackForLibrary : library.getFeedbackList()) {
            FeedBackLibraryDTO feedBackLibraryDTO = FeedBackLibraryDTO.builder()
                    .id(feedBackForLibrary.getId())
                    .message(feedBackForLibrary.getMessage())
                    .ball(feedBackForLibrary.getBall())
                    .createdBy(feedBackForLibrary.getCreatedBy().getFirstName() + " " +
                                feedBackForLibrary.getCreatedBy().getLastName())
                    .build();
            feedBackLibraryDTOS.add(feedBackLibraryDTO);
        }

        ResLibrary resLibrary = ResLibrary.builder()
                .libraryId(library.getId())
                .libraryName(library.getName())
                .lat(library.getLat())
                .lng(library.getLng())
                .fileId(library.getFile() != null ? library.getFile().getId():null)
                .feedBackLibraryDTOList(feedBackLibraryDTOS)
                .build();
        return new ApiResponse(resLibrary);
    }


    public ApiResponse updateLibrary(Long id,ReqLibrary reqLibrary) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library == null) {
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        library.setId(id);
        library.setName(reqLibrary.getName());
        library.setLat(reqLibrary.getLat());
        library.setLng(reqLibrary.getLng());
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
