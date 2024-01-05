package com.example.pilot.domain.login.member.application;


import com.example.pilot.domain.login.member.dto.RequestMemberFacade;
import com.example.pilot.domain.login.member.dto.ResponseMemberFacade;

public interface MemberFacadeService {

    ResponseMemberFacade.Information signup(RequestMemberFacade.Register registerDto);
}
