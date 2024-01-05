package com.example.pilot.domain.login.admin.application.impl;

import com.example.pilot.domain.login.account.application.AccountService;
import com.example.pilot.domain.login.account.dto.RequestAccount;
import com.example.pilot.domain.login.account.dto.ResponseAccount;
import com.example.pilot.domain.login.admin.application.AdminFacadeService;
import com.example.pilot.domain.login.admin.dto.RequestAdminFacade;
import com.example.pilot.domain.login.admin.dto.ResponseAdminFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminFacadeServiceImpl implements AdminFacadeService {

    private final AccountService accountService;

    @Transactional
    @Override
    public ResponseAdminFacade.Information signup(RequestAdminFacade.Register registerDto) {
        ResponseAccount.Information response = accountService.registerAdmin(RequestAccount.RegisterAdmin.builder()
                        .nickname(registerDto.nickname())
                        .password(registerDto.password())
                        .username(registerDto.username())
                .build());

        return ResponseAdminFacade.Information.builder()
                .authoritySet(response.authoritySet())
                .nickname(response.nickname())
                .tokenWeight(response.tokenWeight())
                .password(response.password())
                .username(response.username())
                .build();
    }
}
