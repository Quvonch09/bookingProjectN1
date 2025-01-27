package com.bookingprojectn1.service;

import com.bookingprojectn1.entity.Followed;
import com.bookingprojectn1.entity.Library;
import com.bookingprojectn1.entity.Payment;
import com.bookingprojectn1.entity.User;
import com.bookingprojectn1.payload.ApiResponse;
import com.bookingprojectn1.payload.FollowedDTO;
import com.bookingprojectn1.payload.ResponseError;
import com.bookingprojectn1.repository.FollowedRepository;
import com.bookingprojectn1.repository.LibraryRepository;
import com.bookingprojectn1.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowedRepository followedRepository;
    private final LibraryRepository libraryRepository;
    private final PaymentRepository paymentRepository;

    public ApiResponse getFollowedByLibrary(Long libraryId) {
        List<Followed> all = followedRepository.findAllByLibraryId(libraryId);
        if (all.isEmpty()){
            return new ApiResponse(ResponseError.NOTFOUND("Followed Library"));
        }
        List<FollowedDTO> followedDTOList = new ArrayList<>();
        for (Followed followed : all) {
            FollowedDTO followedDTO = FollowedDTO.builder()
                    .id(followed.getId())
                    .userName(followed.getUser().getFirstName() + " " + followed.getUser().getLastName())
                    .followedDate(followed.getFollowedDate())
                    .build();
            followedDTOList.add(followedDTO);
        }
        return new ApiResponse(followedDTOList);
    }


    public ApiResponse saveFollow(Long libraryId, User user){
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library == null){
            return new ApiResponse(ResponseError.NOTFOUND("Library"));
        }

        List<Payment> allByPayer = paymentRepository.findAllByPayer(user);
        for (Payment payment : allByPayer) {
            if (payment.getPayer() != user && !payment.getLibrary().getId().equals(libraryId)) {
                return new ApiResponse(ResponseError.DEFAULT_ERROR("Siz hali bu kutubxona uchun to'lov qilmagansiz"));
            }
        }

        Followed followed = Followed.builder()
                .followedDate(LocalDate.now())
                .user(user)
                .build();
        followedRepository.save(followed);
        library.getFollowedList().add(followed);
        libraryRepository.save(library);
        return new ApiResponse("Successfully saved follow");
    }





    public ApiResponse deleteFollow(Long libraryId, User user) {
        followedRepository.deleteAll(followedRepository.findAllFollowed(user.getId(), libraryId));
        return new ApiResponse("Successfully deleted");
    }
}
