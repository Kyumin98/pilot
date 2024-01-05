package com.example.pilot.domain.login.account.application;

import com.example.pilot.domain.login.account.dto.ResponseAccount;
import com.example.pilot.domain.login.account.dto.RequestAccount;

public interface AccountService
{
    ResponseAccount.Token authenticate(String username, String password);

    ResponseAccount.Token refreshToken(String refreshToken);

    void invalidateRefreshTokenByUsername(String username);

    ResponseAccount.Information registerMember(RequestAccount.RegisterMember registerMemberDto);

    ResponseAccount.Information registerAdmin(RequestAccount.RegisterAdmin registerAdminDto);

    ResponseAccount.Information getAccountWithAuthorities(String username);

    ResponseAccount.Information getMyAccountWithAuthorities();
}