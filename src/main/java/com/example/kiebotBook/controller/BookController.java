package com.example.kiebotBook.controller;


import com.example.kiebotBook.model.BookDTO;
import com.example.kiebotBook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;



    @GetMapping("/search")
    public List<BookDTO> searchBooks(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer publicationYearFrom,
            @RequestParam(required = false) Integer publicationYearTo,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) String loanedByMemberId,
            @RequestParam(required = false) String reservedByMemberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title,asc") String sort) throws BadRequestException {
        
        return bookService.searchBook(
                isbn, title, authorName, genre,
                publicationYearFrom, publicationYearTo,
                publisher, isAvailable,
                loanedByMemberId, reservedByMemberId,
                page, size, sort);
    }

}
