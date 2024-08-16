package com.maids.library_system.book.services.impl;

import com.maids.library_system.book.entities.Book;
import com.maids.library_system.book.models.request.BookReqModel;
import com.maids.library_system.book.models.response.BookResModel;
import com.maids.library_system.book.repositories.BookRepository;
import com.maids.library_system.book.services.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper bookMapper;

    @Override
    @Transactional
    public BookResModel createBook(BookReqModel bookReqModel) {
        Book book = new Book();
        mapBookReqModelToBook(book, bookReqModel);
        book = bookRepository.save(book);
        return BookResModel.builder().id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .build();

    }

    @Override
    @CachePut(value = "books", key = "#id")
    @Transactional
    public BookResModel updateBook(long id, BookReqModel bookReqModel) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        mapBookReqModelToBook(updatedBook, bookReqModel);
        updatedBook = bookRepository.save(updatedBook);
        return bookMapper.map(updatedBook, BookResModel.class);
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public BookResModel getBookById(long id) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return bookMapper.map(updatedBook, BookResModel.class);
    }

    @Override
    public List<BookResModel> getAllBooks() {
        return Collections.singletonList(bookMapper.map(bookRepository.findAll(), BookResModel.class));
    }

    @Override
    @CacheEvict(value = "books", key = "#id")
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    private void mapBookReqModelToBook(Book book, BookReqModel bookReqModel) {
        book.setAuthor(bookReqModel.getAuthor());
        book.setIsbn(bookReqModel.getIsbn());
        book.setTitle(bookReqModel.getTitle());
        book.setPublicationYear(bookReqModel.getPublicationYear());
    }
}
