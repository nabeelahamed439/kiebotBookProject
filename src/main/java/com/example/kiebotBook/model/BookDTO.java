package com.example.kiebotBook.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookDTO {
        private Long id;
        private String isbn;
        private String title;
        private String genre;
        private Integer publicationYear;
        private String publisher;
        private Integer totalCopies;
        private Integer availableCopies;

        private AuthorDTO author;
        private List<LoanSummary> currentLoansSummary;
        private List<ReservationSummary> activeReservationsSummary;







    }


