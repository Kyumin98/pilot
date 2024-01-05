package com.example.pilot.domain.login.account.application.impl;

import com.example.pilot.domain.login.account.application.LoginService;
import com.example.pilot.domain.login.account.dao.LoginRepository;
import com.example.pilot.domain.login.account.domain.User;
import com.example.pilot.domain.login.account.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    @Override
    public LoginDTO.Response checkUser(String userId, String userPassword) {
        User user = loginRepository.
                findByUserIdAndUserPassword(userId, userPassword).
                orElseThrow(RuntimeException::new);
        return new LoginDTO.Response(user);
    }

    @Override
    public boolean saveUserData(LoginDTO.Request dto) {
        loginRepository.save(dto.toEntity());
        return false;
    }

    @Override
    public boolean updateUserData(LoginDTO.Request dto) {
        User user = loginRepository.
                findByUserIdAndUserPassword(dto.getUserId(), dto.getUserPassword()).
                orElseThrow(RuntimeException::new);
        user.modifyUserData(
                dto.getName(),
                dto.getAddress(),
                dto.getPhoneNumber());
        return false;
    }

    @Override
    public boolean deleteUserData(LoginDTO dto) {
        return false;
    }

}
