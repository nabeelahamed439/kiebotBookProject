package com.example.kiebotBook.repository;

import com.example.kiebotBook.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan,Long> {
    @Query("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId")
    List<BookLoan> findByBookId(@Param("bookId") Long bookId);
}
