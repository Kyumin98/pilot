package com.example.pilot.domain.login.account.domain;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user_data")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false, length = 16, unique = true)
    private String userId;

    @Column(nullable = false, length = 30)
    private String userPassword;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(nullable = false)
    private Date birthday;

    @Column(length = 100)
    private String address;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private Date regDate;

    public void modifyUserData(String name, String address, String phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
