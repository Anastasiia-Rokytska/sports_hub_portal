package com.company.sportHubPortal;

import com.company.sportHubPortal.Controllers.UserController;
import com.company.sportHubPortal.Database.User;
import com.company.sportHubPortal.Database.UserRole;
import com.company.sportHubPortal.Services.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class SignInTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    private String url = "http://localhost:" + "/user/sign-up";

    @Test
    public void successful() throws Exception{
        User user = new User("testFirstName", "textLastName", "testmain@gmail.com", "password", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                .content(new Gson().toJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("role").value(user.getRole().toString()));
    }

    @Test
    public void emptyFirstName() throws Exception{
        User user = new User(null, "textLastName", "testmain@gmail.com", "password", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void emptyLastName() throws Exception{
        User user = new User("testFirstName", null, "testmain@gmail.com", "password", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void wrongEmail() throws Exception{
        User user = new User("testFirstName", "textLastName", "testemail", "password", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void emailAlreadyExists() throws Exception{
        User user = new User("testFirstName", "textLastName", "testmain@gmail.com", "password", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(409));
    }

    @Test
    public void wrongPassword() throws Exception{
        User user = new User("testFirstName", "textLastName", "newtestmail@gmail.com", "pswd", UserRole.ROLE_USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}
