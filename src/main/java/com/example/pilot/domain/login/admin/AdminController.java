package com.example.pilot.domain.login.admin;

import com.example.pilot.domain.login.admin.application.AdminFacadeService;
import com.example.pilot.domain.login.admin.dto.RequestAdminFacade;
import com.example.pilot.domain.login.admin.dto.ResponseAdminFacade;
import com.example.pilot.global.security.dto.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminFacadeService adminFacadeService;

    public AdminController(AdminFacadeService adminFacadeService) {
        this.adminFacadeService = adminFacadeService;
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> signup(
            @Valid @RequestBody RequestAdminFacade.Register registerDto
    ) {
        ResponseAdminFacade.Information userInfo = adminFacadeService.signup(registerDto);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(userInfo)
                .build();
        return ResponseEntity.ok(response);
    }
}
