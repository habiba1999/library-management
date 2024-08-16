package com.maids.library_system.book.services;

import com.maids.library_system.book.models.request.BookReqModel;
import com.maids.library_system.book.models.response.BookResModel;

import java.util.List;

public interface BookService {
    BookResModel createBook(BookReqModel bookReqModel);

    BookResModel updateBook(long bookId, BookReqModel bookReqModel);

    BookResModel getBookById(long bookId);

    List<BookResModel> getAllBooks();

    void deleteBookById(long bookId);
}
