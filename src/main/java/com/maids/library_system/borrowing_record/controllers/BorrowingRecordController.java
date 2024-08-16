package com.maids.library_system.borrowing_record.controllers;

import com.maids.library_system.borrowing_record.services.BorrowingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/services")
@Validated
@AllArgsConstructor
@Tag(name = "Borrowing Services")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    @PostMapping(path = "/borrow/{bookId}/patron/{patronId}")
    @Operation(summary = "borrow a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrowed the book successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to borrow the book")
    })
    public ResponseEntity<Long> borrowABook(@PathVariable("bookId") long bookId,
                                            @PathVariable("patronId") long patronId) {
        return new ResponseEntity<>(borrowingRecordService.borrowABook(bookId, patronId), HttpStatus.OK);
    }

    @PutMapping(path = "/return/{bookId}/patron/{patronId}")
    @Operation(summary = "return a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned the book successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to return the book")
    })
    public ResponseEntity<Long> returnABook(@PathVariable("bookId") long bookId,
                                            @PathVariable("patronId") long patronId) {
        return new ResponseEntity<>(borrowingRecordService.returnABook(bookId, patronId), HttpStatus.OK);
    }


}