package org.olegalimov.examples.social.network.rest;

import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.dto.CreateUserResponseDto;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.service.TarantoolUserService;
import org.olegalimov.examples.social.network.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final TarantoolUserService tarantoolUserService;

    @PostMapping("/register")
    public CreateUserResponseDto registerUser(@RequestBody UserDto userDto) {
        return new CreateUserResponseDto(userService.registerUser(userDto));
    }

    @GetMapping("/get/{id}")
    public UserDto getByUserId(@PathVariable String id) {
        return userService.findByUserId(id);
    }

    @GetMapping("/search")
    public List<UserDto> searchUser(
            @RequestParam(name = "first_name") String firstName,
            @RequestParam(name = "last_name") String secondName) {
        return userService.findByNames(firstName, secondName);
    }

    @GetMapping("/tarantool/search")
    public List<UserDto> searchUserInTarantool(
            @RequestParam(name = "first_name") String firstName,
            @RequestParam(name = "last_name") String lastName) {
        return tarantoolUserService.findByNames(firstName, lastName);
    }
}
