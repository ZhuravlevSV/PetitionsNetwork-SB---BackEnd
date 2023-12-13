package zhurasem.project.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhurasem.project.api.dto.UpdateUserDto;
import zhurasem.project.api.dto.UserDto;
import zhurasem.project.api.exceptions.EntityStateException;
import zhurasem.project.business.UserService;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    List<UserDto> getAll() {
        return convertManyToDto(userService.readAll());
    }

    @PostMapping("/users")
    UserDto create(@RequestBody UserDto newUser) throws EntityStateException {
        var user = this.userService.create(convertToEntity(newUser));
        return convertToDto(
                this.userService.readById(user.getId()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        ));
    }

    @GetMapping("/users/{id}")
    UserDto get(@PathVariable String id) {
        return convertToDto(
                userService.readById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"))
        );
    }

    @PutMapping("/users/{id}")
    UserDto update(@RequestBody UpdateUserDto userDto, @PathVariable String id) {
        var user = userService.readById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Not Found")
                );
        try {
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            this.userService.update(user);
        } catch (EntityStateException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is not unique", exception);
        }
        return convertToDto(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    // Convertors:

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private List<UserDto> convertManyToDto(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .toList()
                .stream()
                .map(this::convertToDto)
                .collect(toList());
    }

}
