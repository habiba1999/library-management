package com.maids.library_system.patron.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.maids.library_system.patron.models.request.PatronReqModel;
import com.maids.library_system.patron.models.response.PatronResModel;
import com.maids.library_system.patron.services.PatronService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patron")
@Validated
@AllArgsConstructor
@Tag(name = "Patron", description = "The Patron API")
public class PatronController {

    PatronService patronService;

    @PostMapping
    @Operation(summary = "Create a new patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patron successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<Long> createPatron(@RequestBody @Valid PatronReqModel patronReqModel) {
        return new ResponseEntity<>(patronService.createPatron(patronReqModel), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update an existing patron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patron successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Patron not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<Long> updatePatron(@PathVariable("id") long patronId, @RequestBody @Valid PatronReqModel patronReqModel) {
        return new ResponseEntity<>(patronService.updatePatron(patronId, patronReqModel), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a patron by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved patron"),
            @ApiResponse(responseCode = "404", description = "Patron not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<PatronResModel> getPatronById(@PathVariable("id") long patronId) {
        return new ResponseEntity<>(patronService.getPatronById(patronId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all patrons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all patrons"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<List<PatronResModel>> getAllPatrons() {
        return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a patron by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted patron"),
            @ApiResponse(responseCode = "404", description = "Patron not found"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    public ResponseEntity<Void> deletePatronById(@PathVariable("id") long patronId) {
        patronService.deletePatronById(patronId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}