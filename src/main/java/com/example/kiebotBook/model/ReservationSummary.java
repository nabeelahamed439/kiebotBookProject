package com.example.kiebotBook.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationSummary {
    private String memberId;
    private LocalDateTime reservationDate;
}
