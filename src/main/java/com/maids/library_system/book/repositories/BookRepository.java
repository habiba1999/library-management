package com.maids.library_system.book.repositories;

import org.springframework.data.repository.CrudRepository;

import com.maids.library_system.book.entities.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
    Book findByIsbn(String isbn);

}
