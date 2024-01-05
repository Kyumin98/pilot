package com.example.pilot.domain.login.account.api;

import com.example.pilot.domain.login.account.application.AccountService;
import com.example.pilot.domain.login.account.dto.RequestAccount;
import com.example.pilot.domain.login.account.dto.ResponseAccount;
import com.example.pilot.global.security.config.CustomJwtFilter;
import com.example.pilot.global.security.dto.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController
{
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/token")
    public ResponseEntity<CommonResponse> authorize(@Valid @RequestBody RequestAccount.Login loginDto) {
        ResponseAccount.Token token = accountService.authenticate(loginDto.username(), loginDto.password());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CustomJwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.accessToken());

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(token)
                .build();

        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/token")
    public ResponseEntity<CommonResponse> refreshToken(@Valid @RequestBody RequestAccount.Refresh refreshDto) {

        ResponseAccount.Token token = accountService.refreshToken(refreshDto.token());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CustomJwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.accessToken());

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(token)
                .build();

        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{username}/token")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CommonResponse> authorize(@PathVariable(name = "username") String username) {
        accountService.invalidateRefreshTokenByUsername(username);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}