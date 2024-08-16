package com.maids.library_system.book.models.response;

import java.io.Serializable;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResModel implements Serializable {

    private Long id;
    private String author;
    private String title;
    private String isbn;
    private int publicationYear;
}
