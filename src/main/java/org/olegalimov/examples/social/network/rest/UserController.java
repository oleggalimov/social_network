package org.olegalimov.examples.social.network.rest;

import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.dto.CreateUserResponseDto;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public CreateUserResponseDto registerUser(@RequestBody UserDto userDto) {
        return new CreateUserResponseDto(userService.registerUser(userDto));
    }

    @GetMapping("/get/{id}")
    public UserDto getByUserId(@PathVariable String id) {
        return userService.findByUserId(id);
    }

    @GetMapping("/slave/get/{id}")
    public UserDto getByUserIdFromSlave(@PathVariable String id) {
        return userService.findByUserId(id, true);
    }

    @GetMapping("/search")
    public List<UserDto> searchUser(
            @RequestParam(name = "first_name") String firstName,
            @RequestParam(name = "last_name") String secondName) {
        return userService.findByNames(firstName, secondName);
    }

    @GetMapping("/slave/search")
    public List<UserDto> searchUserFromSlave(
            @RequestParam(name = "first_name") String firstName,
            @RequestParam(name = "last_name") String secondName) {
        return userService.findByNames(firstName, secondName, true);
    }
}
