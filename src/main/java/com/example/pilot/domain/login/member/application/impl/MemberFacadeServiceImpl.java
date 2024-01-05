package com.example.pilot.domain.login.member.application.impl;

import com.example.pilot.domain.login.account.application.AccountService;
import com.example.pilot.domain.login.account.dto.RequestAccount;
import com.example.pilot.domain.login.account.dto.ResponseAccount;
import com.example.pilot.domain.login.member.application.MemberFacadeService;
import com.example.pilot.domain.login.member.dto.RequestMemberFacade;
import com.example.pilot.domain.login.member.dto.ResponseMemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberFacadeServiceImpl implements MemberFacadeService {

    private final AccountService accountService;

    @Override
    public ResponseMemberFacade.Information signup(RequestMemberFacade.Register registerDto) {
        ResponseAccount.Information response = accountService.registerMember(RequestAccount.RegisterMember.builder()
                        .nickname(registerDto.nickname())
                        .username(registerDto.username())
                        .password(registerDto.password())
                .build());

        return ResponseMemberFacade.Information.builder()
                .authoritySet(response.authoritySet())
                .nickname(response.nickname())
                .tokenWeight(response.tokenWeight())
                .password(response.password())
                .username(response.username())
                .build();
    }
}