package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false,length = 255)
    private String token;

    @Column(name = "token_type", nullable = false,length = 50)
    private  String tokenType;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "expired")
    private boolean expired;

    @Column(name = "revoked")
    private boolean revoked;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}
