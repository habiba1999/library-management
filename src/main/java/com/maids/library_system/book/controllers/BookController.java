package com.maids.library_system.book.controllers;

import com.maids.library_system.book.models.request.BookReqModel;
import com.maids.library_system.book.models.response.BookResModel;
import com.maids.library_system.book.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
@AllArgsConstructor
@Tag(name = "Library Management - Book Services")
public class BookController {


    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Book already exists")
    })
    public ResponseEntity<BookResModel> createBook(
            @RequestBody @Valid BookReqModel bookReqModel) {
        return new ResponseEntity<>(bookService.createBook(bookReqModel), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update an existing book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResModel> updateBook(@PathVariable("id") long bookId,
                                                   @RequestBody @Valid BookReqModel bookReqModel) {
        return new ResponseEntity<>(bookService.updateBook(bookId, bookReqModel), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResModel> getBookById(@PathVariable("id") long bookId) {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    public ResponseEntity<List<BookResModel>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBookById(@PathVariable("id") long bookId) {
        bookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
