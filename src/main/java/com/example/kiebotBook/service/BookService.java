package com.example.kiebotBook.service;

import com.example.kiebotBook.entity.Book;
import com.example.kiebotBook.entity.BookLoan;
import com.example.kiebotBook.entity.Reservation;
import com.example.kiebotBook.exception.BadRequestException;
import com.example.kiebotBook.model.AuthorDTO;
import com.example.kiebotBook.model.BookDTO;
import com.example.kiebotBook.model.LoanSummary;
import com.example.kiebotBook.model.ReservationSummary;
import com.example.kiebotBook.repository.BookLoanRepository;
import com.example.kiebotBook.repository.BookRepository;
import com.example.kiebotBook.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookLoanRepository bookLoanRepository;
    private final ReservationRepository reservationRepository;



    public List<BookDTO> searchBook(String isbn,
                                    String title,
                                    String authorName,
                                    String genre,
                                    Integer publicationYearFrom,
                                    Integer publicationYearTo,
                                    String publisher,
                                    Boolean isAvailable,
                                    String loanedByMemberId,
                                    String reservedByMemberId,
                                    int page,
                                    int size,
                                    String sort) throws BadRequestException {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));



        isbn = (isbn == null || isbn.isEmpty()) ? "ALL" : isbn;
        title = (title == null || title.isEmpty()) ? "ALL" : title;
        authorName = (authorName == null || authorName.isEmpty()) ? "ALL" : authorName;
        publicationYearFrom = (publicationYearFrom == null) ? Integer.MIN_VALUE : publicationYearFrom;
        publicationYearTo = (publicationYearTo == null) ? Integer.MAX_VALUE : publicationYearTo;
        publisher = (publisher == null || publisher.isEmpty()) ? "ALL" : publisher;
        isAvailable = isAvailable == null || isAvailable;
        loanedByMemberId = (loanedByMemberId == null || loanedByMemberId.isEmpty()) ? "ALL" : loanedByMemberId;
        reservedByMemberId = (reservedByMemberId == null || reservedByMemberId.isEmpty()) ? "ALL" : reservedByMemberId;



        if (genre != null) {
            boolean isValidGenre  = Arrays.stream(Book.Genre.values())
                    .anyMatch(genreValue -> genreValue.name().equalsIgnoreCase(genre));

            if (!isValidGenre)
                throw new BadRequestException( "Invalid parameter value","genre","Must be one of " + Arrays.toString(Book.Genre.values()));
        }


        List<Book> books = bookRepository.searchBooks(
                    isbn, title, authorName, genre,
                    publicationYearFrom, publicationYearTo,
                    publisher, isAvailable,
                    loanedByMemberId, reservedByMemberId,pageable
            );


        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
        
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setGenre(String.valueOf(book.getGenre()));
        dto.setPublicationYear(book.getPublicationYear());
        dto.setPublisher(book.getPublisher());
        dto.setTotalCopies(book.getTotalCopies());

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(book.getAuthor().getId());
        authorDTO.setName(book.getAuthor().getName());
        authorDTO.setBirthDate(book.getAuthor().getBirthDate());
        dto.setAuthor(authorDTO);

        List<BookLoan> bookLoan = bookLoanRepository.findByBookId(book.getId());
        List<Reservation> reservationList = reservationRepository.findByBookId(book.getId());

        List<LoanSummary> loanSummaryList = bookLoan.stream().map(loan -> {
            LoanSummary loanSummary = new LoanSummary();
            loanSummary.setMemberId(loan.getMember().getId());
            loanSummary.setDueDate(loan.getDueDate());
            return loanSummary;
        }).toList();

        List<ReservationSummary> reservationSummaryList = reservationList.stream().map(reservation -> {
            ReservationSummary reservationSummary = new ReservationSummary();
            reservationSummary.setMemberId(reservation.getMember().getId());
            reservationSummary.setReservationDate(reservation.getReservationDate());
            return reservationSummary;
        }).toList();

        dto.setCurrentLoansSummary(loanSummaryList);
        dto.setActiveReservationsSummary(reservationSummaryList);

        long activeLoans = bookLoan.stream()
                .filter(loan -> loan.getStatus() == BookLoan.LoanStatus.ACTIVE)
                .count();


        dto.setAvailableCopies(book.getTotalCopies() - (int) activeLoans);

        return dto;
    }
}
