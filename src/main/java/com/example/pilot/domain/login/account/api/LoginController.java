//package com.example.pilot.domain.login.account.api;
//
//import com.example.pilot.domain.login.account.application.impl.LoginServiceImpl;
//import com.example.pilot.domain.login.account.dto.LoginDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import java.util.*;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api")
//public class LoginController {
//
//    private final LoginServiceImpl loginService;
//
//    @PostMapping(value="/login")
//    public LoginDTO.Response test(@RequestBody HashMap<String, String> userData){
//        return loginService.checkUser((String) userData.get("user_id"), (String) userData.get("user_pw"));
//    }
//}
