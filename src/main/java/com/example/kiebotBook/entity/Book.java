package com.example.kiebotBook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotBlank
    @Column(name = "isbn", nullable = false, unique = true)
    @Pattern(regexp = "ISBN-[0-9]{10,13}")
    private String isbn;
    
    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;
    
    @Column(name = "publication_year")
    private Integer publicationYear;
    
    @Column(name = "publisher")
    private String publisher;
    
    @NotNull
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies = 1;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    
    public enum Genre {
        FICTION,
        SCIENCE,
        HISTORY,
        FANTASY,
        MYSTERY,
        BIOGRAPHY,
        TECHNOLOGY,
        TEXTBOOK
    }
}
