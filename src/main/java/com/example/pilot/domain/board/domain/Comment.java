package com.example.pilot.domain.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger commentId;

    @Column(nullable = false)
    private BigInteger boardId;

    @Column(length = 500)
    private String detail;

    @Column(nullable = false, length = 127)
    private String commentAuthor;
}
