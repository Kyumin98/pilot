package com.example.pilot.domain.login.account.api;


import com.example.pilot.domain.login.account.application.AccountService;
import com.example.pilot.domain.login.account.dto.ResponseAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HelloController {

    private final AccountService accountService;

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<ResponseAccount.Information> getMyUserInfo(@AuthenticationPrincipal User user) {
        System.out.println(user.getUsername() + " " + user.getAuthorities());
        return ResponseEntity.ok(accountService.getMyAccountWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseAccount.Information> getUserInfo(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(accountService.getAccountWithAuthorities(username));
    }
}