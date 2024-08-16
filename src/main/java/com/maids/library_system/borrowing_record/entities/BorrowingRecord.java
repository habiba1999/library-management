package com.maids.library_system.borrowing_record.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.maids.library_system.book.entities.Book;
import com.maids.library_system.patron.entities.Patron;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name ="BorrowingRecords")
public class BorrowingRecord implements Serializable {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable=false)
	    private LocalDate borrowingDate;

	    private LocalDate returnDate;

	    @ManyToOne
	    @JoinColumn(name = "PATRON_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_BorrowingRecord_Patron"))
	    private Patron patron;

	    @ManyToOne
	    @JoinColumn(name = "BOOK_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_BorrowingRecord_Book"))
	    private Book book;
}
