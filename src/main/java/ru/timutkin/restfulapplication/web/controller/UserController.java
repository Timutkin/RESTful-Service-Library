package ru.timutkin.restfulapplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.service.UserService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.UserBookRequest;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;
import ru.timutkin.restfulapplication.web.response.user.UserBookResponse;


@RestController
@AllArgsConstructor
@RequestMapping(value =WebConstant.VERSION_URL+"/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user of library",
            responses = {
                @ApiResponse(responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Long.class)
                        )),
                @ApiResponse(description = ResponseConstant.USER_WITH_EMAIL_ALREADY_EXISTS, responseCode = "409",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO){
        Long userId = userService.createUser(userDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse(description = ResponseConstant.USER_WITH_ID_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDTO.class)
                            )),
                    @ApiResponse(description = ResponseConstant.USER_WITH_ID_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }


    @PutMapping
    @Operation(summary = "Update a user",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDTO.class)
                            )),
                    @ApiResponse(description = ResponseConstant.USER_WITH_ID_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = ResponseConstant.USER_WITH_EMAIL_ALREADY_EXISTS, responseCode = "409",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))),
            })
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return ResponseEntity.ok(userDTO);
    }
    @Operation(summary = "Bind book to user",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = ResponseConstant.USER_WITH_ID_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PostMapping("/bind")
    public ResponseEntity<UserBookResponse> addBookToUser(@RequestBody UserBookRequest userBookRequest){
        UserBookResponse userBookResponse = userService.bindBookToUser(userBookRequest);
        return ResponseEntity.ok(userBookResponse);
    }



}
