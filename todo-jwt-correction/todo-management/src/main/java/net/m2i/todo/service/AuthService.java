package net.m2i.todo.service;

import net.m2i.todo.dto.LoginDto;
import net.m2i.todo.dto.RegisterDto;

public interface AuthService  {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);

    Long getIdByUserName(String username);

}
