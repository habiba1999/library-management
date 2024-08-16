package com.maids.library_system.book.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import com.maids.library_system.book.entities.Book;
import com.maids.library_system.book.models.request.BookReqModel;
import com.maids.library_system.book.models.response.BookResModel;
import com.maids.library_system.book.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {BookServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link BookServiceImpl#createBook(BookReqModel)}
     */
    @Test
    void testCreateBook() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        when(bookRepository.save(Mockito.<Book>any())).thenReturn(book);

        // Act
        BookResModel actualCreateBookResult = bookServiceImpl.createBook(new BookReqModel("JaneDoe", "Dr", "Isbn", 1));

        // Assert
        verify(bookRepository).save(isA(Book.class));
        assertEquals("Dr", actualCreateBookResult.getTitle());
        assertEquals("Isbn", actualCreateBookResult.getIsbn());
        assertEquals("JaneDoe", actualCreateBookResult.getAuthor());
        assertEquals(1, actualCreateBookResult.getPublicationYear());
        assertEquals(1L, actualCreateBookResult.getId().longValue());
    }

    /**
     * Method under test: {@link BookServiceImpl#createBook(BookReqModel)}
     */
    @Test
    void testCreateBook2() {
        // Arrange
        when(bookRepository.save(Mockito.<Book>any())).thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));

        // Act and Assert
        assertThrows(ResponseStatusException.class,
                () -> bookServiceImpl.createBook(new BookReqModel("JaneDoe", "Dr", "Isbn", 1)));
        verify(bookRepository).save(isA(Book.class));
    }

    /**
     * Method under test: {@link BookServiceImpl#updateBook(long, BookReqModel)}
     */
    @Test
    void testUpdateBook() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(1);
        book2.setTitle("Dr");
        when(bookRepository.save(Mockito.<Book>any())).thenReturn(book2);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        BookResModel buildResult = BookResModel.builder().author("JaneDoe").id(1L).publicationYear(1).title("Dr").build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any())).thenReturn(buildResult);

        // Act
        BookResModel actualUpdateBookResult = bookServiceImpl.updateBook(1L, new BookReqModel("JaneDoe", "Dr", "Isbn", 1));

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findById(eq(1L));
        verify(bookRepository).save(isA(Book.class));
        assertEquals("Dr", actualUpdateBookResult.getTitle());
        assertEquals("JaneDoe", actualUpdateBookResult.getAuthor());
        assertNull(actualUpdateBookResult.getIsbn());
        assertEquals(1, actualUpdateBookResult.getPublicationYear());
        assertEquals(1L, actualUpdateBookResult.getId().longValue());
    }

    /**
     * Method under test: {@link BookServiceImpl#updateBook(long, BookReqModel)}
     */
    @Test
    void testUpdateBook2() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);

        Book book2 = new Book();
        book2.setAuthor("JaneDoe");
        book2.setId(1L);
        book2.setIsbn("Isbn");
        book2.setPublicationYear(1);
        book2.setTitle("Dr");
        when(bookRepository.save(Mockito.<Book>any())).thenReturn(book2);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any()))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));

        // Act and Assert
        assertThrows(ResponseStatusException.class,
                () -> bookServiceImpl.updateBook(1L, new BookReqModel("JaneDoe", "Dr", "Isbn", 1)));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findById(eq(1L));
        verify(bookRepository).save(isA(Book.class));
    }

    /**
     * Method under test: {@link BookServiceImpl#updateBook(long, BookReqModel)}
     */
    @Test
    void testUpdateBook3() {
        // Arrange
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Mock modelMapper to return the correct type
        BookResModel buildResult = BookResModel.builder()
                .author("JaneDoe")
                .id(1L)
                .publicationYear(1)
                .title("Dr")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class,
                () -> bookServiceImpl.updateBook(1L, new BookReqModel("JaneDoe", "Dr", "Isbn", 1)));

        // Verify interactions
        verify(modelMapper, never()).map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any()); // Since the book is not found, mapping should never occur.
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#updateBook(long, BookReqModel)}
     */
    @Test
    void testUpdateBook4() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.save(Mockito.<Book>any())).thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        BookReqModel bookReqModel = BookReqModel.builder().author("JaneDoe").publicationYear(1).title("Dr").build();

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> bookServiceImpl.updateBook(1L, bookReqModel));
        verify(bookRepository).findById(eq(1L));
        verify(bookRepository).save(isA(Book.class));
    }

    /**
     * Method under test: {@link BookServiceImpl#getBookById(long)}
     */
    @Test
    void testGetBookById() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        BookResModel buildResult = BookResModel.builder().author("JaneDoe").id(1L).publicationYear(1).title("Dr").build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any())).thenReturn(buildResult);

        // Act
        BookResModel actualBookById = bookServiceImpl.getBookById(1L);

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findById(eq(1L));
        assertEquals("Dr", actualBookById.getTitle());
        assertEquals("JaneDoe", actualBookById.getAuthor());
        assertNull(actualBookById.getIsbn());
        assertEquals(1, actualBookById.getPublicationYear());
        assertEquals(1L, actualBookById.getId().longValue());
    }

    /**
     * Method under test: {@link BookServiceImpl#getBookById(long)}
     */
    @Test
    void testGetBookById2() {
        // Arrange
        Book book = new Book();
        book.setAuthor("JaneDoe");
        book.setId(1L);
        book.setIsbn("Isbn");
        book.setPublicationYear(1);
        book.setTitle("Dr");
        Optional<Book> ofResult = Optional.of(book);
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any()))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> bookServiceImpl.getBookById(1L));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#getBookById(long)}
     */
    @Test
    void testGetBookById3() {
        // Arrange
        Optional<Book> emptyResult = Optional.empty();
        when(bookRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Mock modelMapper to handle the correct return type for BookResModel
        BookResModel buildResult = BookResModel.builder()
                .author("JaneDoe")
                .id(1L)
                .publicationYear(1)
                .title("Dr")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any())).thenReturn(buildResult);

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> bookServiceImpl.getBookById(1L));

        // Verify the interactions
        verify(modelMapper, never()).map(Mockito.<Object>any(), Mockito.<Class<BookResModel>>any()); // This mapping should never be called since the book is not found.
        verify(bookRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#getAllBooks()}
     */
    @Test
    void testGetAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
                .thenThrow(new ResponseStatusException(HttpStatusCode.valueOf(200)));

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> bookServiceImpl.getAllBooks());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findAll();
    }

    /**
     * Method under test: {@link BookServiceImpl#getAllBooks()}
     */
    @Test
    void testGetAllBooks2() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        BookResModel buildResult = BookResModel.builder().author("JaneDoe").id(1L).publicationYear(1).title("Dr").build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(buildResult);

        // Act
        List<BookResModel> actualAllBooks = bookServiceImpl.getAllBooks();

        // Assert
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(bookRepository).findAll();
        assertEquals(1, actualAllBooks.size());
        BookResModel getResult = actualAllBooks.get(0);
        assertEquals("Dr", getResult.getTitle());
        assertEquals("JaneDoe", getResult.getAuthor());
        assertNull(getResult.getIsbn());
        assertEquals(1, getResult.getPublicationYear());
        assertEquals(1L, getResult.getId().longValue());
    }

    /**
     * Method under test: {@link BookServiceImpl#deleteBookById(long)}
     */
    @Test
    void testDeleteBookById() {
        // Arrange
        doNothing().when(bookRepository).deleteById(Mockito.<Long>any());

        // Act
        bookServiceImpl.deleteBookById(1L);

        // Assert that nothing has changed
        verify(bookRepository).deleteById(eq(1L));
    }

    /**
     * Method under test: {@link BookServiceImpl#deleteBookById(long)}
     */
    @Test
    void testDeleteBookById2() {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatusCode.valueOf(200))).when(bookRepository)
                .deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () -> bookServiceImpl.deleteBookById(1L));
        verify(bookRepository).deleteById(eq(1L));
    }
}
