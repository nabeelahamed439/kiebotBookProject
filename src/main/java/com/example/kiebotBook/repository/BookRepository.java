package com.example.kiebotBook.repository;

import com.example.kiebotBook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @Query("SELECT DISTINCT b FROM Book b "
            + "LEFT JOIN FETCH b.author a "
            + "WHERE (:isbn = 'ALL' OR b.isbn = :isbn) "
            + "AND (:title = 'ALL' OR (b.title) ILIKE (CONCAT('%', :title, '%'))) "
            + "AND (:authorName = 'ALL' OR (a.name) ILIKE (CONCAT('%', :authorName, '%'))) "
            + "AND (:genre = 'ALL' OR UPPER(b.genre) = UPPER(:genre)) "
            + "AND (:publicationYearFrom = -214748364 OR b.publicationYear >= :publicationYearFrom) "
            + "AND (:publicationYearTo = 214748364 OR b.publicationYear <= :publicationYearTo) "
            + "AND (:publisher = 'ALL' OR (b.publisher) ILIKE (CONCAT('%', :publisher, '%'))) "
            + "AND ("
            + "(:isAvailable = TRUE AND b.totalCopies > (SELECT COUNT(bl) FROM BookLoan bl WHERE bl.book = b AND bl.status = 'ACTIVE')) OR "
            + "(:isAvailable = FALSE AND b.totalCopies <= (SELECT COUNT(bl) FROM BookLoan bl WHERE bl.book = b AND bl.status = 'ACTIVE')))"
            + "AND (:loanedByMemberId = 'ALL' OR EXISTS (SELECT 1 FROM BookLoan bl3 WHERE bl3.book = b AND bl3.member.memberId = :loanedByMemberId AND bl3.status = 'ACTIVE')) "
            + "AND (:reservedByMemberId = 'ALL' OR EXISTS (SELECT 1 FROM Reservation r2 WHERE r2.book = b AND r2.member.memberId = :reservedByMemberId AND r2.status IN ('PENDING', 'READY_FOR_PICKUP')))"
    )
    Page<Book> searchBooks(
            @Param("isbn") String isbn,
            @Param("title") String title,
            @Param("authorName") String authorName,
            @Param("genre") String genre,
            @Param("publicationYearFrom") Integer publicationYearFrom,
            @Param("publicationYearTo") Integer publicationYearTo,
            @Param("publisher") String publisher,
            @Param("isAvailable") Boolean isAvailable,
            @Param("loanedByMemberId") String loanedByMemberId,
            @Param("reservedByMemberId") String reservedByMemberId
            ,Pageable pageable
    );
} 