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
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger boardId;

    @Column(nullable = false, length = 127)
    private String title;

    @Column(length = 1000)
    private String detail;

    @Column(nullable = false, length = 127)
    private String author;

    @ManyToMany
    @JoinTable(
            name = "board_comment",
            joinColumns = {@JoinColumn(name = "board_id", referencedColumnName = "board_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id", referencedColumnName = "comment_id")}
    )
    private List<Comment> commentList;

}

