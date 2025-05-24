package com.example.kiebotBook.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
}
