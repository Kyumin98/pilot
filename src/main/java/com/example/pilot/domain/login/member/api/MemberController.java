package com.example.pilot.domain.login.member.api;

import com.example.pilot.domain.login.member.application.MemberFacadeService;
import com.example.pilot.domain.login.member.dto.RequestMemberFacade;
import com.example.pilot.domain.login.member.dto.ResponseMemberFacade;
import com.example.pilot.global.security.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class MemberController {
    private final MemberFacadeService memberFacadeService;

    public MemberController(MemberFacadeService memberFacadeService) {
        this.memberFacadeService = memberFacadeService;
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> signup(
            @Valid @RequestBody RequestMemberFacade.Register registerDto
    ) {
        ResponseMemberFacade.Information userInfo = memberFacadeService.signup(registerDto);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userInfo)
                .build();
        return ResponseEntity.ok(response);
    }
}