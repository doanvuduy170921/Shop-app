package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private Long id;
    @Column(name = "fullname", nullable = false,length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false,length = 10)
    private String phoneNumber;

    @Column(name = "address",length = 200)
    private String address;

    @Column(name = "password", nullable = false,length = 100)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private Integer facebookAccountId;

    @Column(name = "google_account_id")
    private Integer googleAccountId;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;
}
