package com.company.sporthubportal;
import com.company.sporthubportal.database.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class LoginTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void validData() throws Exception{
        User user = new User("email1@gmail.com", "qwerty");
        this.mockMvc.perform(post("/user/login")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void unvalidData() throws Exception{
        User user = new User("emai@gmail.com", "qwer123ty");
        this.mockMvc.perform(post("/user/login")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void wrongPassword() throws Exception{
        User user = new User("email1@gmail.com", "qwer123ty");
        this.mockMvc.perform(post("/user/login")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void wrongEmail() throws Exception{
        User user = new User("ema@gmail.com", "qwerty");
        this.mockMvc.perform(post("/user/login")
                        .content(new Gson().toJson(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}
