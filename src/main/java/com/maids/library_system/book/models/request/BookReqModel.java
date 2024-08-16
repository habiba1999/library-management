package com.maids.library_system.book.models.request;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookReqModel implements Serializable {

	@NotNull
	private String author;
	@NotNull
	private String title;
	@Size(max = 13)
	private String isbn;
	@Min(1)
	private int publicationYear;
}
