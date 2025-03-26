package com.metacoding.bankv1.account.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "history_tb")
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // (PK)
    private Integer withdrawNumber; // 1111 (FK)
    private Integer depositNumber; // 2222 (FK)
    private Integer amount;
    private Integer withdrawBalance;
    private Integer depositBalance;
    private Timestamp createdAt; // 생성 날짜 (insert 된 시간)
}
