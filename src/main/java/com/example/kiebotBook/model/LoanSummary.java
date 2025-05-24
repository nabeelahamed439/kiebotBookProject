package com.example.kiebotBook.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public  class LoanSummary {
    private Long memberId;
    private LocalDate dueDate;

}
