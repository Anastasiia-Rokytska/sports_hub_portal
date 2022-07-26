package com.company.sportHubPortal;

import com.company.sportHubPortal.Controllers.UserController;
import com.company.sportHubPortal.Models.User;
import com.company.sportHubPortal.Models.UserRole;
import com.company.sportHubPortal.Services.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets/signUp")
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
        User user = new User("testFirstName", "textLastName", "testmain@gmail.com", "password", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                .content(new Gson().toJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("role").value(user.getRole().toString()))
                .andDo(document("{methodName}"));
    }

    @Test
    public void emptyFirstName() throws Exception{
        User user = new User(null, "textLastName", "testmain@gmail.com", "password", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().is(400))
                .andDo(document("{methodName}"));
    }

    @Test
    public void emptyLastName() throws Exception{
        User user = new User("testFirstName", null, "testmain@gmail.com", "password", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().is(400))
                .andDo(document("{methodName}"));
    }

    @Test
    public void wrongEmail() throws Exception{
        User user = new User("testFirstName", "textLastName", "testemail", "password", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().is(400))
                .andDo(document("{methodName}"));
    }

    @Test
    public void emailAlreadyExists() throws Exception{
        User user = new User("testFirstName", "textLastName", "testmain@gmail.com", "password", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().is(409))
                .andDo(document("{methodName}"));
    }

    @Test
    public void wrongPassword() throws Exception{
        User user = new User("testFirstName", "textLastName", "newtestmail@gmail.com", "pswd", UserRole.USER);
        this.mockMvc.perform(post("/user/sign-up")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().is(400))
                .andDo(document("{methodName}"));
    }
}
