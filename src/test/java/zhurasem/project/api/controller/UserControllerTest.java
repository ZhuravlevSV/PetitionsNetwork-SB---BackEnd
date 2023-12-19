package zhurasem.project.api.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zhurasem.project.api.converter.UserConverter;
import zhurasem.project.api.dto.UserDto;
import zhurasem.project.business.UserService;
import zhurasem.project.domain.User;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserConverter userConverter;

    @Test
    public void testCreate() throws Exception {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        UserDto userDto = new UserDto("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(userConverter.toDto(user)).thenReturn(userDto);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{" +
                                "\"username\":\"zhurasem\"," +
                                "\"email\":\"zhurasem@fit.cvut.cz\"," +
                                "\"password\":\"123\"" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadAll() throws Exception {
        User user1 = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");
        User user2 = new User("putinvla", "putinvla@fit.cvut.cz", "123");
        List<User> users = List.of(user1, user2);

        UserDto userDto1 = new UserDto("zhurasem", "zhurasem@fit.cvut.cz", "123");
        UserDto userDto2 = new UserDto("putinvla", "putinvla@fit.cvut.cz", "123");
        List<UserDto> userDtos = List.of(userDto1, userDto2);

        Mockito.when(userService.readAll()).thenReturn(users);
        Mockito.when(userConverter.toDtos(users)).thenReturn(userDtos);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].username", Matchers.is("zhurasem")))
                .andExpect(jsonPath("$[1].username", Matchers.is("putinvla")));
    }

    @Test
    public void testRead() throws Exception {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        UserDto userDto = new UserDto("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(user));
        Mockito.when(userConverter.toDto(user)).thenReturn(userDto);

        mockMvc.perform(get("/users/zhurasem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is("zhurasem")))
                .andExpect(jsonPath("$.email", Matchers.is("zhurasem@fit.cvut.cz")))
                .andExpect(jsonPath("$.password", Matchers.is("123")));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        UserDto userDto = new UserDto("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(user));
        Mockito.when(userService.update(user)).thenReturn(user);
        Mockito.when(userConverter.toDto(user)).thenReturn(userDto);

        mockMvc.perform(put("/users/zhurasem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{" +
                                "\"username\":\"zhurasem\",\"email\":\"zhurasem@fit.cvut.cz\",\"password\":\"123\"" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User("zhurasem", "zhurasem@fit.cvut.cz", "123");

        UserDto userDto = new UserDto("zhurasem", "zhurasem@fit.cvut.cz", "123");

        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(userConverter.toDto(user)).thenReturn(userDto);
        Mockito.when(userService.readById("zhurasem")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{" +
                                        "\"username\":\"zhurasem\"," +
                                        "\"email\":\"zhurasem@fit.cvut.cz\"," +
                                        "\"password\":\"123\"" +
                                        "}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/users/zhurasem"))
                .andExpect(status().isOk());
    }
}
