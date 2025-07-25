package com.example.testtask.controller;

import com.example.testtask.entity.User;
import com.example.testtask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operations related to users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @Operation(summary = "Get all users", description = "Returns a list of all users from all databases")
    @ApiResponse(
            responseCode = "200",
            description = "Successful retrieval of users",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Array of users",
                                    summary = "Example with 2 users",
                                    value = "[{\"id\":1,\"username\":\"john_doe\",\"name\":\"John\",\"surname\":\"Doe\"}," +
                                            "{\"id\":2,\"username\":\"anna_smith\",\"name\":\"Anna\",\"surname\":\"Smith\"}]"
                            ),
                            @ExampleObject(
                                    name = "Empty array",
                                    summary = "Example with empty array if no users exist",
                                    value = "[]"
                            )
                    },
                    array = @ArraySchema(schema = @Schema(implementation = User.class))
            )
    )
    public List<User> findAll() {
        return userService.getAllUsers();
    }

}
