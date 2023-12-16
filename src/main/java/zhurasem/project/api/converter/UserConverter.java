package zhurasem.project.api.converter;

import org.springframework.stereotype.Component;
import zhurasem.project.api.dto.UserDto;
import zhurasem.project.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserConverter() {}

    public User toEntity(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
    }

    public List<User> toEntities(Iterable<UserDto> userDtos) {
        List<User> entities = new ArrayList<>();
        for(UserDto userDto : userDtos)
            entities.add(toEntity(userDto));
        return entities;
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getUsername(), user.getEmail(), user.getPassword());
    }

    public List<UserDto> toDtos(Iterable<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for(User user : users)
            dtos.add(toDto(user));
        return dtos;
    }

}
