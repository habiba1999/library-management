package com.maids.library_system.borrowing_record.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.maids.library_system.book.entities.Book;
import com.maids.library_system.book.repositories.BookRepository;
import com.maids.library_system.borrowing_record.entities.BorrowingRecord;
import com.maids.library_system.borrowing_record.repositories.BorrowingRecordRepository;
import com.maids.library_system.patron.entities.Patron;
import com.maids.library_system.patron.repositories.PatronRepository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BorrowingRecordServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BorrowingRecordServiceImplTest {
    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BorrowingRecordServiceImpl borrowingRecordServiceImpl;

    @MockBean
    private PatronRepository patronRepository;

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult2 = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book2);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron2);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord);

        // Act
        Long actualBorrowABookResult = borrowingRecordServiceImpl.borrowABook(1L, 1L);

        // Assert
        verify(bookRepository).findById(eq(1L));
        verify(patronRepository).findById(eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertNull(actualBorrowABookResult);
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook2() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult2 = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> borrowingRecordServiceImpl.borrowABook(1L, 1L));
        verify(bookRepository).findById(eq(1L));
        verify(patronRepository).findById(eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook3() {
        // Arrange
        Optional<Patron> emptyResult = Optional.empty();
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> borrowingRecordServiceImpl.borrowABook(1L, 1L));
        verify(bookRepository).findById(eq(1L));
        verify(patronRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook4() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> borrowingRecordServiceImpl.borrowABook(1L, 1L));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook5() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult2 = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book2);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron2);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord);

        // Act
        Long actualBorrowABookResult = borrowingRecordServiceImpl.borrowABook(2L, 1L);

        // Assert
        verify(patronRepository).findById(eq(1L));
        verify(bookRepository).findById(eq(2L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertNull(actualBorrowABookResult);
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook6() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult2 = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book2);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron2);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord);

        // Act
        Long actualBorrowABookResult = borrowingRecordServiceImpl.borrowABook(3L, 1L);

        // Assert
        verify(patronRepository).findById(eq(1L));
        verify(bookRepository).findById(eq(3L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertNull(actualBorrowABookResult);
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#borrowABook(long, long)}
     */
    @Test
    void testBorrowABook7() {
        // Arrange
        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");
        Optional<Patron> ofResult = Optional.of(patron);
        when(patronRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");
        Optional<Book> ofResult2 = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book2);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron2);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord);

        // Act
        Long actualBorrowABookResult = borrowingRecordServiceImpl.borrowABook(4L, 1L);

        // Assert
        verify(patronRepository).findById(eq(1L));
        verify(bookRepository).findById(eq(4L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertNull(actualBorrowABookResult);
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#returnABook(long, long)}
     */
    @Test
    void testReturnABook() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");

        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord2 = new BorrowingRecord();
        borrowingRecord2.setBook(book2);
        borrowingRecord2.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord2.setId(1L);
        borrowingRecord2.setPatron(patron2);
        borrowingRecord2.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord2);
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act
        Long actualReturnABookResult = borrowingRecordServiceImpl.returnABook(1L, 1L);

        // Assert
        verify(borrowingRecordRepository).findByBookIdAndPatronId(eq(1L), eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertEquals(1L, actualReturnABookResult.longValue());
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#returnABook(long, long)}
     */
    @Test
    void testReturnABook2() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");

        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenThrow(new RuntimeException("foo"));
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> borrowingRecordServiceImpl.returnABook(1L, 1L));
        verify(borrowingRecordRepository).findByBookIdAndPatronId(eq(1L), eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#returnABook(long, long)}
     */
    @Test
    void testReturnABook3() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");

        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord2 = new BorrowingRecord();
        borrowingRecord2.setBook(book2);
        borrowingRecord2.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord2.setId(1L);
        borrowingRecord2.setPatron(patron2);
        borrowingRecord2.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord2);
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act
        Long actualReturnABookResult = borrowingRecordServiceImpl.returnABook(2L, 1L);

        // Assert
        verify(borrowingRecordRepository).findByBookIdAndPatronId(eq(2L), eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertEquals(1L, actualReturnABookResult.longValue());
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#returnABook(long, long)}
     */
    @Test
    void testReturnABook4() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");

        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord2 = new BorrowingRecord();
        borrowingRecord2.setBook(book2);
        borrowingRecord2.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord2.setId(1L);
        borrowingRecord2.setPatron(patron2);
        borrowingRecord2.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord2);
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act
        Long actualReturnABookResult = borrowingRecordServiceImpl.returnABook(3L, 1L);

        // Assert
        verify(borrowingRecordRepository).findByBookIdAndPatronId(eq(3L), eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertEquals(1L, actualReturnABookResult.longValue());
    }

    /**
     * Method under test: {@link BorrowingRecordServiceImpl#returnABook(long, long)}
     */
    @Test
    void testReturnABook5() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(3);
        book.setTitle("Dr");

        Patron patron = new Patron();
        patron.setEmail("jane.doe@example.org");
        patron.setId(1L);
        patron.setMobile("Mobile");
        patron.setName("Name");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord.setId(1L);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setReturnDate(LocalDate.of(1970, 1, 1));

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(3);
        book2.setTitle("Dr");

        Patron patron2 = new Patron();
        patron2.setEmail("jane.doe@example.org");
        patron2.setId(1L);
        patron2.setMobile("Mobile");
        patron2.setName("Name");

        BorrowingRecord borrowingRecord2 = new BorrowingRecord();
        borrowingRecord2.setBook(book2);
        borrowingRecord2.setBorrowingDate(LocalDate.of(1970, 1, 1));
        borrowingRecord2.setId(1L);
        borrowingRecord2.setPatron(patron2);
        borrowingRecord2.setReturnDate(LocalDate.of(1970, 1, 1));
        when(borrowingRecordRepository.save(Mockito.<BorrowingRecord>any())).thenReturn(borrowingRecord2);
        when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act
        Long actualReturnABookResult = borrowingRecordServiceImpl.returnABook(4L, 1L);

        // Assert
        verify(borrowingRecordRepository).findByBookIdAndPatronId(eq(4L), eq(1L));
        verify(borrowingRecordRepository).save(isA(BorrowingRecord.class));
        assertEquals(1L, actualReturnABookResult.longValue());
    }
}
