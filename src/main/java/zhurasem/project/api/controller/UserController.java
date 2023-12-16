package zhurasem.project.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhurasem.project.api.converter.UserConverter;
import zhurasem.project.api.dto.UpdateUserDto;
import zhurasem.project.api.dto.UserDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/users")
    List<UserDto> getAll() {
        return userConverter.toDtos(userService.readAll());
    }

    @PostMapping("/users")
    UserDto create(@RequestBody UserDto userDto) {
        try {
            return userConverter.toDto(userService.create(userConverter.toEntity(userDto)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User ID already taken");
        }
    }

    @GetMapping("/users/{id}")
    UserDto get(@PathVariable String id) {
        return userConverter.toDto(userService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")));
    }

    @PutMapping("/users/{id}")
    UserDto update(@RequestBody UpdateUserDto userDto, @PathVariable String id) {
        var user = userService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        try {
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            this.userService.update(user);
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is not unique", e);
        }
        return userConverter.toDto(user);
    }

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable String id) {
        userService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        userService.deleteById(id);
    }

}
