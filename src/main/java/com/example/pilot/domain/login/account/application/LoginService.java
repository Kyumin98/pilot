package com.example.pilot.domain.login.account.application;

import com.example.pilot.domain.login.account.dto.LoginDTO;

public interface LoginService {

    public LoginDTO.Response checkUser(String userId, String userPassword);

    public boolean saveUserData(LoginDTO.Request dto);

    public boolean updateUserData(LoginDTO.Request dto);

    public boolean deleteUserData(LoginDTO dto);
}
