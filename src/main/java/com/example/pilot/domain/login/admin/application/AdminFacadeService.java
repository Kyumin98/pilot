package com.example.pilot.domain.login.admin.application;


import com.example.pilot.domain.login.admin.dto.RequestAdminFacade;
import com.example.pilot.domain.login.admin.dto.ResponseAdminFacade;

public interface AdminFacadeService {

    ResponseAdminFacade.Information signup(RequestAdminFacade.Register registerDto);
}
