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
import ru.timutkin.restfulapplication.web.response.Response;


@RestController
@AllArgsConstructor
@RequestMapping(value =WebConstant.VERSION_URL+"/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    UserService userService;

    @PostMapping
    @Operation(summary = "Created new user of library",
            responses = {
                @ApiResponse(responseCode = "200",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Long.class)
                        )),
                @ApiResponse(description = ResponseConstant.USER_WITH_EMAIL_ALREADY_EXISTS, responseCode = "409",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = Response.class)))})
    public ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO){
        Long userId = userService.createUser(userDTO);
        return ResponseEntity.ok(userId);
    }

    @Operation(summary = "Deleted user with id ",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse(description = ResponseConstant.USER_WITH_ID_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Response.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }


}
