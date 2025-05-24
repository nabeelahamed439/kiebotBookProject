package com.example.kiebotBook.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationSummary {
    private Long memberId;
    private LocalDateTime reservationDate;
}
