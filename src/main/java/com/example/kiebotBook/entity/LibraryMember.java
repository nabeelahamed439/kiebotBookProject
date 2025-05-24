package com.example.kiebotBook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "library_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryMember {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotBlank
    @Column(name = "member_id", nullable = false, unique = true)
    @Pattern(regexp = "MEM-[A-Z0-9]{6}")
    private String memberId;
    
    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "membership_date", nullable = false)
    private LocalDate membershipDate = LocalDate.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus status = MemberStatus.ACTIVE;

    public enum MemberStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }
}
