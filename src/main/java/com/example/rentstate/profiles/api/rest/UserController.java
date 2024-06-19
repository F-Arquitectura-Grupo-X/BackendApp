package com.example.rentstate.profiles.api.rest;

import com.example.rentstate.profiles.api.resource.userresource.UserResponse;
import com.example.rentstate.profiles.api.resource.userresource.UpdateUserResource;
import com.example.rentstate.profiles.domain.model.aggregates.User;
import com.example.rentstate.profiles.domain.service.RatingService;
import com.example.rentstate.profiles.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {

    private final UserService userService;

    private final RatingService ratingService;

    public UserController(UserService userService, RatingService ratingService) {
        this.userService = userService;
        this.ratingService = ratingService;
    }


    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserResponse> responseUsers= users.stream()
                .map(user -> new UserResponse(user))
                .collect(Collectors.toList());

        return responseUsers;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getById(userId);

        UserResponse userResponse = new UserResponse(user.get());
        userResponse.setRankPoints(this.ratingService.getAverageRatingByRatedUser(user.get()));
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserResource updateUserResource) {

        Optional<User> userToUpdate = userService.getById(updateUserResource.getId());

        if(userToUpdate.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        userToUpdate.get().updateUser(updateUserResource);
        userService.update(userToUpdate.get());
        UserResponse userResponse = new UserResponse(userToUpdate.get());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}