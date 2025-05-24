package com.example.kiebotBook.repository;


import com.example.kiebotBook.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.book.id = :bookId AND status = 'ACTIVE'")
    List<Reservation> findByBookId(@Param("bookId") Long bookId);
}
