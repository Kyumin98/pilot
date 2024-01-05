package com.example.pilot.domain.login.account.application.impl;

import com.example.pilot.domain.login.account.domain.AccountErrorCode;
import com.example.pilot.domain.login.account.dto.ResponseAccount;
import com.example.pilot.domain.login.account.application.AccountService;
import com.example.pilot.domain.login.account.dao.AccountRepository;
import com.example.pilot.domain.login.account.domain.Account;
import com.example.pilot.domain.login.account.domain.AccountAdapter;
import com.example.pilot.domain.login.account.domain.Authority;
import com.example.pilot.domain.login.account.dto.RequestAccount;
import com.example.pilot.global.exception.ApplicationException;
import com.example.pilot.global.exception.CommonErrorCode;
import com.example.pilot.global.security.AccessTokenProvider;
import com.example.pilot.global.security.RefreshTokenProvider;
import com.example.pilot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    @Override
    public ResponseAccount.Token authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = accessTokenProvider.createToken(authentication);

        Long tokenWeight = ((AccountAdapter)authentication.getPrincipal()).getAccount().getTokenWeight();
        String refreshToken = refreshTokenProvider.createToken(authentication, tokenWeight);

        return ResponseAccount.Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseAccount.Token refreshToken(String refreshToken) {
        if(!refreshTokenProvider.validateToken(refreshToken)) throw new ApplicationException(AccountErrorCode.INVALID_REFRESH_TOKEN);

        Authentication authentication = refreshTokenProvider.getAuthentication(refreshToken);
        Account account = accountRepository.findOneWithAuthoritiesByUsername(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException(authentication.getName() + "을 찾을 수 없습니다"));
        if(account.getTokenWeight() > refreshTokenProvider.getTokenWeight(refreshToken)) throw new ApplicationException(AccountErrorCode.INVALID_REFRESH_TOKEN);

        String accessToken = accessTokenProvider.createToken(authentication);
        return ResponseAccount.Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    @Override
    public void invalidateRefreshTokenByUsername(String username) {
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "-> 찾을 수 없습니다"));
        account.increaseTokenWeight(); // 더티체킹에 의해 엔티티 변화 반영
    }

    @Transactional
    @Override
    public ResponseAccount.Information registerMember(RequestAccount.RegisterMember registerMemberDto) {
        Optional<Account> accountOptional = accountRepository.findOneWithAuthoritiesByUsername(
                registerMemberDto.username());

        if (accountOptional.isPresent()) {
            throw new ApplicationException(CommonErrorCode.CONFLICT, "이미 가입되어있는 유저");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_MEMBER")
                .build();

        Account user = Account.builder()
                .username(registerMemberDto.username())
                .password(passwordEncoder.encode(registerMemberDto.password()))
                .nickname(registerMemberDto.nickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return ResponseAccount.Information.of(accountRepository.save(user));
    }

    @Transactional
    @Override
    public ResponseAccount.Information registerAdmin(RequestAccount.RegisterAdmin registerAdminDto) {
        Optional<Account> accountOptional = accountRepository.findOneWithAuthoritiesByUsername(
                registerAdminDto.username());

        if (accountOptional.isPresent()) {
            throw new ApplicationException(CommonErrorCode.CONFLICT, "이미 가입되어있는 유저");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        Account user = Account.builder()
                .username(registerAdminDto.username())
                .password(passwordEncoder.encode(registerAdminDto.password()))
                .nickname(registerAdminDto.nickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return ResponseAccount.Information.of(accountRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseAccount.Information getAccountWithAuthorities(String username) {
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "-> 찾을 수 없습니다"));
        return ResponseAccount.Information.of(account);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseAccount.Information getMyAccountWithAuthorities() {
        Account account = securityUtil.getCurrentUsername()
                .flatMap(accountRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new UsernameNotFoundException("security context로부터 찾을 수 없습니다"));
        return ResponseAccount.Information.of(account);
    }
}