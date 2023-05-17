package com.example.app.controller;

import com.example.app.dto.UserCreatedCountDTO;
import com.example.app.dto.UserReviewCountDTO;
import com.example.app.dto.model.ReviewDTO;
import com.example.app.dto.model.RolDTO;
import com.example.app.dto.model.UserPreferenceDTO;
import com.example.app.dto.model.UserProfileDTO;
import com.example.app.exceptions.AppException;
import com.example.app.model.Rol;
import com.example.app.model.User;
import com.example.app.model.UserPreference;
import com.example.app.service.IReviewService;
import com.example.app.service.IUserProfileService;
import com.example.app.service.IUserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping(path="/users")
    public @ResponseBody ResponseEntity<Page<UserProfileDTO>> getUsers(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userProfileService.getAllUserProfiles(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<UserProfileDTO> getUser(
            @PathVariable("id") String handle,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(user.getHandle(), handle))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        UserProfileDTO userProfileDTO = userProfileService.getUserProfileById(handle);
        if(userProfileDTO == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
        }
    }

    @PatchMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> updateUser(
            @PathVariable("id") String handle,
            @Valid @RequestBody UserProfileDTO userProfileDTO,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if(!user.getUserRol().isUpdate_all() && (!user.getUserRol().isUpdate_own() || !Objects.equals(user.getHandle(), handle))) {
            response.put("error", "Unauthorized to update resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        userProfileService.updateUserProfileWithId(handle, userProfileDTO);
        response.put("message", "User profile updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path="/users/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteUser(
            @PathVariable("id") String handle,
            @RequestAttribute("user") User user
    ) {
        Map<String, String> response = new HashMap<>();
        if(!user.getUserRol().isDelete_all() && (!user.getUserRol().isDelete_own() || !Objects.equals(user.getHandle(), handle))) {
            response.put("error", "Unauthorized to delete resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        userProfileService.deleteUserProfileWithId(handle);
        response.put("message", "User profile deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path="/users/{id}/reviews", produces = "application/json")
    public @ResponseBody ResponseEntity<Page<ReviewDTO>> getReviews(
            @PathVariable("id") String handle,
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(user.getHandle(), handle))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(reviewService.getReviewsForUser(handle, pageNumber, pageSize), HttpStatus.OK);
        } catch (AppException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/user-review-counts")
    public @ResponseBody ResponseEntity<Page<UserReviewCountDTO>> getUsersReviewCountPage(
            @RequestParam
            Integer pageNumber,
            @RequestParam
            @Min(value=4, message = "pageSize should be at least 4")
            @Max(value=10, message = "pageSize should be at most 10")
            Integer pageSize,
            @RequestAttribute("user") User user
    ){
        if(!user.getUserRol().isRead_all()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userProfileService.getUserReviewCountPage(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping(path="/user-created-count/{handle}", produces = "application/json")
    public @ResponseBody ResponseEntity<UserCreatedCountDTO> getUserCreatedCount(
            @PathVariable("handle")
            String handle,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(user.getHandle(), handle))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userProfileService.getUserCreatedCount(handle), HttpStatus.OK);
    }

    @GetMapping(path="/users/{handle}/rol")
    public @ResponseBody ResponseEntity<RolDTO> getUserRol(
            @PathVariable("handle") String handle,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(handle, user.getHandle()))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(RolDTO.fromRol(userService.getUserByHandle(handle).getUserRol()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/users/{handle}/preference")
    public @ResponseBody ResponseEntity<UserPreferenceDTO> getUserPreference(
            @PathVariable("handle") String handle,
            @RequestAttribute("user") User user
    ) {
        if(!user.getUserRol().isRead_all() && (!user.getUserRol().isRead_own() || !Objects.equals(handle, user.getHandle()))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(UserPreferenceDTO.fromUserPreference(userService.getUserByHandle(handle).getUserPreference()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path="/users/{handle}/preference")
    public @ResponseBody ResponseEntity<Map<String, String>> setUserPreference(
            @PathVariable("handle") String handle,
            @RequestAttribute("user") User user,
            @RequestBody UserPreferenceDTO userPreferenceDTO
    ) {
        Map<String, String> response = new HashMap<>();
        if(!user.getUserRol().isUpdate_all() && (!user.getUserRol().isUpdate_own() || !Objects.equals(handle, user.getHandle()))) {
            response.put("error", "Unauthorized to update resource");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        try {
            userService.setUserPreference(handle, UserPreferenceDTO.toUserPreference(userPreferenceDTO, userService.getUserByHandle(handle)));
            response.put("message", "updated preference");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
