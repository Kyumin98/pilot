package com.example.pilot.domain.login.account.dto;

import com.example.pilot.domain.login.account.domain.User;
import lombok.*;

import java.sql.Date;

public class LoginDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Integer seq;
        private String userId;
        private String userPassword;
        private String name;
        private Date birthday;
        private String address;
        private String phoneNumber;
        private Date regDate;

        public User toEntity(){
            return User.builder()
                    .seq(seq)
                    .userId(userId)
                    .userPassword(userPassword)
                    .name(name)
                    .birthday(birthday)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .regDate(regDate)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private final Integer seq;
        private final String userId;
        private final String userPassword;
        private final String name;
        private final Date birthday;
        private final String address;
        private final String phoneNumber;
        private final Date regDate;

        public Response(User user){
            this.seq = user.getSeq();
            this.userId = user.getUserId();
            this.userPassword = user.getUserPassword();
            this.name = user.getName();
            this.birthday = user.getBirthday();
            this.address = user.getAddress();
            this.phoneNumber = user.getPhoneNumber();
            this.regDate = user.getRegDate();
        }
    }
}
