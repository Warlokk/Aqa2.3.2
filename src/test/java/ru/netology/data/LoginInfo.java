package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor

public class LoginInfo {
    private final String login;
    private String password;
    private String status;

}
